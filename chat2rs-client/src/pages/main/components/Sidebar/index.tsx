import { FC, useEffect } from "react";
import styles from "./index.module.css"
import { usePageStore } from "../../store";

const Sidebar: FC = () => {
    const fileList = usePageStore((state) => state.fileList);
    const addFile = usePageStore((state) => state.addFile);
    const selectedFile = usePageStore((state) => state.selectedFile);
    const updateSelectedFile = usePageStore((state) => state.updateSelectedFile);

    useEffect(() => {
        // 添加一个事件监听器，监听文件拖入事件
        const dropEvent = (event: any) => {
            event.preventDefault(); // 阻止默认行为，避免浏览器打开文件

            // 获取拖入的文件列表
            const files: File[] = event?.dataTransfer?.files ?? [];

            // 处理拖入的文件
            for (const file of files) {
                addFile(file);
                updateSelectedFile(file);
            }
        };
        document.addEventListener('drop', dropEvent);
        // 阻止拖入文件时打开文件的默认行为
        document.addEventListener('dragover', (event) => {
            event.preventDefault();
        });
        return () => {
            document.removeEventListener('drop', dropEvent)
        }
    }, [addFile]);

    const onClick = (file: File) => {
        updateSelectedFile(file);
    }

    return <div className={styles.container}>
        {
            fileList.map((file) => {
                const { path, name } = file;
                const isSelect = selectedFile?.path === path;
                return <div onClick={() => onClick(file)} className={`${styles['file-item']} ${isSelect && styles['file-item_selected']}`} key={path}>{name}</div>
            })
        }
    </div>
}

export default Sidebar;