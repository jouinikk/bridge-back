package com.example.cars.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter// ✅ exposes fields
@NoArgsConstructor // ✅ needed by Jackson
@AllArgsConstructor
public class GroupNameDTO {
    private String groupName;
    private long groupId;
}
