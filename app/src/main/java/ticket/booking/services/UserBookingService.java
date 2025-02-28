package ticket.booking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import ticket.booking.entities.Ticket;
import ticket.booking.entities.Train;
import ticket.booking.entities.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticket.booking.util.UserServiceUtil;

import javax.swing.text.html.Option;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Logger;

public class UserBookingService  {
    private User user;
    private final String USERS_PATH = "app/src/main/java/ticket/booking/localDb/users.json";
    private ObjectMapper objectMapper =  new ObjectMapper();
    private List<User>userList;
    private TrainService trainService;
    private Logger logger;

    public UserBookingService(User user) throws IOException {
        this.user  = user;
        readUserListFromFile();
    }
    public UserBookingService() throws IOException,MismatchedInputException {
        System.out.println("Hello world!");
        readUserListFromFile();
    }
    public User loginUser(String name, String password) throws IOException {
        // Ensure user list is loaded
        readUserListFromFile();

        // Find the user with matching credentials
        return userList.stream()
                .filter(user -> user.getName().equals(name) &&
                        UserServiceUtil.checkPassword(password, user.getHashedPassword()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));
    }


    public Boolean signUp(User user1) throws IOException {
        userList.add(user1);
        try {
            saveUserListToFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Boolean.TRUE;
    }

    private void readUserListFromFile() throws IOException, MismatchedInputException {
        File users = new File(USERS_PATH);
        // At runtime, Using TypeReference to retain the Generic information
        this.userList = objectMapper.readValue(users, new TypeReference<List<User>>() {});
    }
    private void fetchUser() throws IOException {

    }
    private void saveUserListToFile() throws IOException {
            File usersFile = new File(USERS_PATH);
            objectMapper.writeValue(usersFile, userList);


    }


    /*
    * The ticketId which matches the ticketId of the User is removed
    * */
    public void cancelBooking(String ticketId) throws IOException{
        user.getTicketsBooked().removeIf(t -> t.getTicketId().equals(ticketId));
        saveUserListToFile();
    }

    public List<Train> getTrains(String source, String destination){
        TrainService trainService = null;
        List<Train> trains = new ArrayList<>();
        try{
            trainService = new TrainService();
            trains = trainService.searchTrains(source,destination);

        }catch (IOException e){
            e.printStackTrace();
        }
        return trains;
    }

    public void bookTicket(User user, String src, String dest, Train trainSelected) throws IOException {

        LocalDate currentDate = LocalDate.now();
        Ticket ticketBooked = new Ticket(UUID.randomUUID().toString(), user.getUserId(), src,dest,currentDate,trainSelected);

        System.out.println("User ID: " + user.getName());

        // Ensure the user's ticket list is initialized
        if (user.getTicketsBooked() == null) {
            user.setTicketsBooked(new ArrayList<>());  // Initialize if null
        }

        // Add the booked ticket
        List<Ticket> tickets = user.getTicketsBooked();
        tickets.add(ticketBooked);
        user.setTicketsBooked(tickets);

        //saveUserListToFile(); //Java 8 date/time type `java.time.LocalDate` not supported by default: add Module "com.fasterxml.jackson.datatype:jackson-datatype-jsr310"
    }
}
