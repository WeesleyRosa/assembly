package com.assembly.infra.common.util;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public final class UriGeneratorUtil {

    private UriGeneratorUtil() { }

    public static URI get(String path, Object value) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path(path)
                .buildAndExpand(value)
                .toUri();
    }

}
