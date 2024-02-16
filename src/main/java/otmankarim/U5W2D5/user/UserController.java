package otmankarim.U5W2D5.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import otmankarim.U5W2D5.exceptions.BadRequestException;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public Page<User> getAllAuthors(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "id") String orderBy) {
        return this.userService.getAuthors(page, size, orderBy);
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable long id) {
        return this.userService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Status Code 201
    public User save(@RequestBody @Validated UserDTO newUserDTO, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.userService.save(newUserDTO);
    }

    @PutMapping("/{id}")
    public User findByIdAndUpdate(@PathVariable int id, @RequestBody @Validated UserDTO updatedUser, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.userService.findByIdAndUpdate(id, updatedUser);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // Status Code 204
    public void findByIdAndDelete(@PathVariable long id) {
        this.userService.findByIdAndDelete(id);
    }
}
