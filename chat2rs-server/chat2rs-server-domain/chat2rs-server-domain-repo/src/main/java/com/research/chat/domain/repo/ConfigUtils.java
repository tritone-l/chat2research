package com.research.chat.domain.repo;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson2.JSON;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.mapping.Environment;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.File;
import java.io.InputStream;
import java.util.Objects;
import java.util.Optional;

/**
 * copy from internet
 * Configure information on the user side
 *
 * @author xxx
 */
@Slf4j
public class ConfigUtils {
    public static String CONFIG_BASE_PATH = System.getProperty("user.home") + File.separator + ".chat2rs";

    private static String preVersion = null;

    private static String version = null;

    public static File versionFile = null;
    public static File configFile;
    private static ConfigJson config = null;

    public static File clientIdFile;
    private static String clientId = null;


    static {
        String environment = Objects.toString(System.getProperty("spring.profiles.active"), "dev");

        version = System.getProperty("chat2rs.version");
        System.out.println(version);
        versionFile = new File(CONFIG_BASE_PATH + File.separator + "config" + File.separator + "version");

        if (!versionFile.exists()) {
            FileUtil.writeUtf8String(version, versionFile);
        } else {
            preVersion = FileUtil.readUtf8String(versionFile);
            FileUtil.del(versionFile);
            FileUtil.writeUtf8String(version, versionFile);
        }

        configFile = new File(
                CONFIG_BASE_PATH + File.separator + "config" + File.separator + "config_" + environment + ".json");
        if (!configFile.exists()) {
            FileUtil.writeUtf8String(JSON.toJSONString(new ConfigJson()), configFile);
        }

        clientIdFile = new File(
                CONFIG_BASE_PATH + File.separator + "config" + File.separator + "client_uuid");
        if (!clientIdFile.exists()) {
            String uuid = UUID.fastUUID().toString(true);
            FileUtil.writeUtf8String(uuid, clientIdFile);
            clientId = uuid;
        }
    }

    public static void updateVersion(String version) {
        if (versionFile == null) {
            log.warn("VERSION_FILE is null");
            return;
        }
        FileUtil.writeUtf8String(version, versionFile);
        ConfigUtils.version = version;
    }

    public static String getLocalVersion() {
        if (versionFile == null) {
            log.warn("VERSION_FILE is null");
            return null;
        }
        if (version != null) {
            return version;
        }
        version = StringUtils.trim(FileUtil.readUtf8String(versionFile));
        return version;
    }

    public static String getLatestLocalVersion() {
        if (versionFile == null) {
            log.warn("VERSION_FILE is null");
            return null;
        }
        return StringUtils.trim(FileUtil.readUtf8String(versionFile));
    }


    public static ConfigJson getConfig() {
        if (config == null) {
            config = JSON.parseObject(StringUtils.trim(FileUtil.readUtf8String(configFile)), ConfigJson.class);
        }
        return config;
    }

    public static String getClientId() {
        if (clientId == null) {
            clientId = StringUtils.trim(FileUtil.readUtf8String(clientIdFile));
        }
        return clientId;
    }

    public static void setConfig(ConfigJson config) {
        String stringConfigJson = JSON.toJSONString(config);
        FileUtil.writeUtf8String(stringConfigJson, configFile);
        ConfigUtils.config = config;
        log.info("set config:{}", stringConfigJson);
    }

    private static String getAppPath() {
        try {
            String jarPath = System.getProperty("project.path");
            return FileUtil.getParent(jarPath, 4);
        } catch (Exception e) {
            log.error("getAppPath error", e);
            return null;
        }
    }

    /**
     * 该方法保证只有一个服务端在运行
     */
    public static void initProcess() {
        try {
            ProcessHandle currentProcess = ProcessHandle.current();
            long pid = currentProcess.pid();
            log.info("current pid {}", pid);
            String environment = StringUtils.defaultString(System.getProperty("spring.profiles.active"), "dev");
            File pidFile = new File(CONFIG_BASE_PATH + File.separator + "config" + File.separator + environment + "app.pid");
            if (!pidFile.exists()) {
                FileUtil.writeUtf8String(String.valueOf(pid), pidFile);
            } else {
                String oldPid = FileUtil.readUtf8String(pidFile);
                log.info("oldPid:{}", oldPid);
                if (StringUtils.isNotBlank(oldPid)) {
                    Optional<ProcessHandle> processHandle = ProcessHandle.of(Long.parseLong(oldPid));
                    processHandle.ifPresent(handle -> {
                        ProcessHandle.Info info = handle.info();
                        String[] arguments = info.arguments().orElse(null);
                        log.info("arguments:{}", JSON.toJSONString(arguments));
                        if (arguments == null) {
                            return;
                        }
                        for (String argument : arguments) {
                            if (StringUtils.equals("chat2rs-server-start.jar", argument)) {
                                handle.destroy();
                                log.info("destroy old process--------");
                                break;
                            }
                            if (argument.contains("chat2research.jar")) {
                                handle.destroy();
                                log.info("destroy old process--------");
                                break;
                            }
                        }
                    });
                }

                FileUtil.writeUtf8String(String.valueOf(pid), pidFile);
            }

        } catch (Exception e) {
            log.error("updatePid error", e);
        }

    }
}
