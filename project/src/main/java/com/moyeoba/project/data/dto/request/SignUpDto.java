package com.moyeoba.project.data.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
@NoArgsConstructor
@Getter
public class SignUpDto {
    @NotNull
    private String phoneNumber;
}
