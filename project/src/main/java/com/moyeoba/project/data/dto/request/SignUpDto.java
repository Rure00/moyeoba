package com.moyeoba.project.data.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
@Getter
public class SignUpDto {
    @NotNull
    private String phoneNumber;
}
