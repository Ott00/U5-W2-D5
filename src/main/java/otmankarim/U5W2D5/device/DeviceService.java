package otmankarim.U5W2D5.device;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import otmankarim.U5W2D5.exceptions.BadRequestException;
import otmankarim.U5W2D5.exceptions.NotFoundException;

@Service
public class DeviceService {
    @Autowired
    private DeviceDAO deviceDAO;

    public Page<Device> getDevices(int pageNumber, int size, String orderBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(orderBy));
        return deviceDAO.findAll(pageable);
    }

    public Device save(DeviceDTO newDeviceDTO) {
        Device device = new Device(
                getDeviceStatus(newDeviceDTO),
                getDeviceType(newDeviceDTO)
        );
        return deviceDAO.save(device);
    }

    public Device findById(long id) {
        return deviceDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Device findByIdAndUpdate(long id, DeviceDTO updatedDevice) {
        Device found = this.findById(id);
        found.setDeviceStatus(getDeviceStatus(updatedDevice));
        found.setDeviceType(getDeviceType(updatedDevice));
        return deviceDAO.save(found);
    }

    public void findByIdAndDelete(long id) {
        Device found = this.findById(id);
        deviceDAO.delete(found);
    }

    public DeviceType getDeviceType(DeviceDTO newDeviceDTO) {
        String deviceTypeString = newDeviceDTO.deviceType().toUpperCase();
        DeviceType deviceType;

        switch (deviceTypeString) {
            case "COMPUTER":
                deviceType = DeviceType.COMPUTER;
                break;
            case "LAPTOP":
                deviceType = DeviceType.LAPTOP;
                break;
            case "PHONE":
                deviceType = DeviceType.PHONE;
                break;
            case "TABLET":
                deviceType = DeviceType.TABLET;
                break;
            default:
                throw new BadRequestException("device type not exist");
        }

        return deviceType;
    }

    public DeviceStatus getDeviceStatus(DeviceDTO newDeviceDTO) {
        String deviceStatusString = newDeviceDTO.deviceStatus().toUpperCase();
        DeviceStatus deviceStatus;

        switch (deviceStatusString) {
            case "AVAILABLE":
                deviceStatus = DeviceStatus.AVAILABLE;
                break;
            case "ASSIGNED":
                deviceStatus = DeviceStatus.ASSIGNED;
                break;
            case "UNDER_MAINTENANCE":
                deviceStatus = DeviceStatus.UNDER_MAINTENANCE;
                break;
            case "DISUSED":
                deviceStatus = DeviceStatus.DISUSED;
                break;
            default:
                throw new BadRequestException("device status not exist");
        }
        return deviceStatus;
    }
}
