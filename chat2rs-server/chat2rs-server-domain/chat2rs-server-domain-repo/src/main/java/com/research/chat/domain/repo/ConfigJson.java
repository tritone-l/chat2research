package com.research.chat.domain.repo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * Author tritone
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ConfigJson {

    /**
     * Last successfully launched version
     */
    private String latestStartupSuccessVersion;

    /**
     * jwt
     */
    private String jwtSecretKey;

    /**
     * The unique ID of the  system
     */
    private String systemUuid;
}
