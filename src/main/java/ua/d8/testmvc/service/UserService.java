package ua.d8.testmvc.service;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ua.d8.testmvc.domain.Book;
import ua.d8.testmvc.domain.User;

import javax.annotation.PostConstruct;
import javax.management.Query;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 */
@Service
public class UserService {
    private final Map<Long, User> users = new ConcurrentHashMap<Long, User>();
    private final Map<Long, Book> books = new ConcurrentHashMap<Long, Book>();

    //fill maps with dummy values
    @PostConstruct
    public void init() {
        for (int i = 0; i<20; i++) {
            Book book = new Book();
            book.setId((long)(10000000.0 * Math.random()));
            book.setTitle("title"+i);

            books.put(book.getId(), book);
        }
        for (int i = 0; i<5; i++) {
            User user = new User();
            user.setId((long)i);
            user.setName("name"+i);
            user.setAge((int)(60.0 * Math.random()+1));
            user.setActive(true);

            for (int j=0; j<2; j++){
                Random random = new Random();
                List<Long> keys = new ArrayList<Long>(books.keySet());
                Long randomKey = keys.get(random.nextInt(keys.size()));
                Book book = books.remove(randomKey);
                user.getBooks().add(book);
            }

            users.put(user.getId(), user);
        }
    }


    public List<User> getAll(){
        return new ArrayList<User>(users.values());
    }

    public User getById(Long id){
        User user = users.get(id);
        if(user==null) throw new UserNotFoundException("user not found");
        return users.get(id);
    }

    public void deleteById(Long id){
        getById(id);

        users.remove(id);
    }

    public void update(User user){
        getById(user.getId());
        users.put(user.getId(),user);
    }
}
