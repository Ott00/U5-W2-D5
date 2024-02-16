package otmankarim.U5W2D5.device;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record DeviceDTO(
        @NotEmpty(message = "device status is mandatory [AVAILABLE, ASSIGNED, UNDER_MAINTENANCE, DISUSED]")
        @NotNull
        String deviceStatus,
        @NotEmpty(message = "device type is mandatory [COMPUTER, LAPTOP, PHONE, TABLET]")
        @NotNull
        String deviceType
) {
}
