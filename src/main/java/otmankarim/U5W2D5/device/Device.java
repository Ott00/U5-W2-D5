package otmankarim.U5W2D5.device;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import otmankarim.U5W2D5.user.User;

@Entity
@Table(name = "devices")
@Getter
@Setter
@NoArgsConstructor
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Setter(AccessLevel.NONE)
    private Long id;
    @Column(name = "device_status")
    @Enumerated(EnumType.STRING)
    private DeviceStatus deviceStatus;
    @Column(name = "device_type")
    @Enumerated(EnumType.STRING)
    private DeviceType deviceType;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Device(DeviceStatus deviceStatus, DeviceType deviceType) {
        this.deviceStatus = deviceStatus;
        this.deviceType = deviceType;
    }
}
