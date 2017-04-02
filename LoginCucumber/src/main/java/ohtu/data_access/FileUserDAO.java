
package ohtu.data_access;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import ohtu.domain.User;

/**
 *
 * @author Samu
 */
public class FileUserDAO implements UserDao {
    
    private File f;
    private Scanner reader;
    private List<User> users;
    private FileWriter filew;

    public FileUserDAO(String fileName) throws FileNotFoundException, IOException {
        f = new File(fileName);
        users = new ArrayList<User>();
        if (!f.exists()) {
            f.createNewFile();
        }
        reader = new Scanner(f);
        addFromFile();
    }
    
    public void addFromFile() {
        List<User> users1 = new ArrayList<User>();
        Scanner r = null;
        try {
            r = new Scanner(f);
        } catch (FileNotFoundException ex) {
            System.out.println("The file was not found.");
        }
        boolean parillinen = true;
        while (r.hasNextLine()) {
            String name = r.nextLine();
            if (!name.isEmpty()) {
                String pw = r.nextLine();
                users1.add(new User(name, pw));
            }
        }
        users = users1;
    }
    
    @Override
    public List<User> listAll() {
        return users;
    }

    @Override
    public User findByName(String name) {
        User ret = null;
        for (User u : users) {
            if (u.getUsername().matches(name)) {
                ret = u;
                break;
            }
        }
        return ret;
    }

    @Override
    public void add(User user) {
        try {
            writeToFile(user.getUsername(), user.getPassword());
        } catch (IOException ex) {
            System.out.println("File does not exist");
        }
        users.add(user);
    }
    
    public void writeToFile(String user, String pw) throws IOException {
        FileWriter fw = new FileWriter(f, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(user);
        bw.newLine();
        bw.write(pw);
        bw.newLine();
        bw.flush();
        fw.close();
    }
    
    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }
}
