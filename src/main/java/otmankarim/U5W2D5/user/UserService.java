package otmankarim.U5W2D5.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import otmankarim.U5W2D5.exceptions.BadRequestException;
import otmankarim.U5W2D5.exceptions.NotFoundException;

@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;

    public Page<User> getAuthors(int pageNumber, int size, String orderBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(orderBy));
        return userDAO.findAll(pageable);
    }

    public User save(UserDTO newUserDTO) {
        userDAO.findByEmail(newUserDTO.email()).ifPresent(author -> {
            throw new BadRequestException("L'email " + newUserDTO.email() + " è già in uso!");
        });
        User user = new User(
                newUserDTO.username(),
                newUserDTO.name(),
                newUserDTO.surname(),
                newUserDTO.email()
        );
        user.setAvatar(createAvatarUrl(newUserDTO));
        return userDAO.save(user);
    }

    public User findById(long id) {
        return userDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public User findByIdAndUpdate(long id, UserDTO updatedUser) {
        User found = this.findById(id);
        found.setUsername(updatedUser.username());
        found.setName(updatedUser.name());
        found.setSurname(updatedUser.surname());
        found.setEmail(updatedUser.email());
        return userDAO.save(found);
    }

    public void findByIdAndDelete(long id) {
        User found = this.findById(id);
        userDAO.delete(found);
    }

    public String createAvatarUrl(UserDTO newUserDTO) {
        return "https://ui-avatars.com/api/?name=" + newUserDTO.name() + "+" + newUserDTO.surname();
    }

}
