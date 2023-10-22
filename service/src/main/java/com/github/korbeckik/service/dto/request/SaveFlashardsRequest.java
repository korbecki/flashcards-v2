package com.github.korbeckik.service.dto.request;

import java.util.List;

public record SaveFlashardsRequest(String name, String description, String icon, Boolean isPublic, List<PageDto> pages) {
}
