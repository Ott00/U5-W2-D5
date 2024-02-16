package otmankarim.U5W2D5.device;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record DeviceDTO(
        @NotEmpty(message = "device status is mandatory")
        @NotNull
        String deviceStatus,
        @NotEmpty(message = "device type is mandatory")
        @NotNull
        String deviceType
) {
}
