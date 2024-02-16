package otmankarim.U5W2D5.device;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import otmankarim.U5W2D5.exceptions.BadRequestException;
import otmankarim.U5W2D5.user.UserMailDTO;

@RestController
@RequestMapping("/devices")
public class DeviceController {
    @Autowired
    private DeviceService deviceService;

    @GetMapping
    public Page<Device> getAllDevices(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size,
                                      @RequestParam(defaultValue = "id") String orderBy) {
        return this.deviceService.getDevices(page, size, orderBy);
    }

    @GetMapping("/{id}")
    public Device findById(@PathVariable long id) {
        return this.deviceService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Status Code 201
    public Device save(@RequestBody @Validated DeviceDTO newDeviceDTO, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.deviceService.save(newDeviceDTO);
    }

    @PutMapping("/{id}")
    public Device findByIdAndUpdate(@PathVariable int id, @RequestBody @Validated DeviceDTO updatedDevice, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.deviceService.findByIdAndUpdate(id, updatedDevice);
    }

    @PatchMapping("/{id}/setUser")
    @ResponseStatus(HttpStatus.OK) // Status Code 200
    public Device setUser(@PathVariable int id, @RequestBody @Validated UserMailDTO userMailDTO, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.deviceService.setUser(id, userMailDTO.email());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // Status Code 204
    public void findByIdAndDelete(@PathVariable long id) {
        this.deviceService.findByIdAndDelete(id);
    }
}
