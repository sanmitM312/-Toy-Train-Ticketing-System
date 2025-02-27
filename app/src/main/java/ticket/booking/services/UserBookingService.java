package ticket.booking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import ticket.booking.entities.Ticket;
import ticket.booking.entities.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticket.booking.util.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;

public class UserBookingService  {
    private User user;
    private final String USERS_PATH = "app/src/main/java/ticket/booking/localDb/users.json";
    private ObjectMapper objectMapper =  new ObjectMapper();
    private List<User>userList;

    public UserBookingService(User user) throws IOException {
        this.user  = user;
        readUserListFromFile();
    }
    public UserBookingService() throws IOException,MismatchedInputException {
        System.out.println("Hello world!");
        readUserListFromFile();
    }
    public Boolean loginUser(User user) throws  IOException{
        // optional to prevent null pointer
        readUserListFromFile();
        Optional<User> foundUser = userList.stream().filter(user1 -> {
            return user1.getName().equals(user.getName()) && UserServiceUtil.checkPassword(user.getPassword(),user1.getHashedPassword());
        }).findFirst();
        return foundUser.isPresent();
    }

    public Boolean signUp(User user1){
        try{
            userList.add(user1);
            saveUserListToFile();
            return Boolean.TRUE;
        }catch(IOException e){
            return Boolean.FALSE;
        }
    }

    private void readUserListFromFile() throws IOException, MismatchedInputException {
        File users = new File(USERS_PATH);
        // At runtime, Using TypeReference to retain the Generic information
        this.userList = objectMapper.readValue(users, new TypeReference<List<User>>() {});
    }

    private void saveUserListToFile() throws IOException{
        File usersFile = new File(USERS_PATH);
        objectMapper.writeValue(usersFile,userList);
    }
    public void fetchBooking(){
        this.user.printTickets();
    }

    /*
    * The ticketId which matches the ticketId of the User is removed
    * */
    public void cancelBooking(String ticketId) throws IOException{
        user.getTicketsBooked().removeIf(t -> t.getTicketId().equals(ticketId));
        saveUserListToFile();
    }
}
