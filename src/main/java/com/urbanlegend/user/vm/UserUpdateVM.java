package com.urbanlegend.user.vm;

import com.urbanlegend.shared.FileType;
import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserUpdateVM {
    @NotNull(message = "{urbanlegend.constraint.displayName.NotNull.message}")
    @Size(min = 4, max = 30)
    private String displayName;

    @FileType(types = {"jpg","png"})
    private String image;
}
