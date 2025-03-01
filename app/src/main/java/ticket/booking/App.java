/*
 * This source file was generated by the Gradle 'init' task
 */
package ticket.booking;

import ticket.booking.entities.Train;
import ticket.booking.entities.User;
import ticket.booking.services.TrainService;
import ticket.booking.services.UserBookingService;
import ticket.booking.util.UserServiceUtil;

import java.io.IOException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class App {
    public static void main(String[] args) throws IOException {
        System.out.println("Running Train Booking System");
        Scanner scanner = new Scanner(System.in);
        int option = 0;

        UserBookingService userBookingService;
        try{
            userBookingService = new UserBookingService();
        }catch(Exception e){
            e.printStackTrace();
            return;
        }

        TrainService trainService;
        try{
            trainService = new TrainService();
        }catch (Exception e){
            e.printStackTrace();
            return;
        }

        do{
            System.out.println("Choose option");
            System.out.println("1. Sign up");
            System.out.println("2. Login");
            System.out.println("3. Fetch Bookings");
            System.out.println("4. Search Trains");
            System.out.println("5. Book a seat");
            System.out.println("6. Cancel my booking");
            System.out.println("7. Exit the Application");

            option = scanner.nextInt();
            switch (option) {
                case 1:
                    System.out.println("Enter the username to signup");
                    String nameToSignUp = scanner.next();
                    System.out.println("Enter the password to signup");
                    String passwordToSignUp = scanner.next();

                    User userToSignup = new User(nameToSignUp, passwordToSignUp,
                            UserServiceUtil.hashPassword(passwordToSignUp),
                            new ArrayList<>(), UUID.randomUUID().toString());
                    userBookingService.signUp(userToSignup);
                    break;
                case 2:
                    System.out.println("Enter the username for login");
                    String nameToLogin = scanner.next();
                    System.out.println("Enter the password for login");
                    String passwordToLogin = scanner.next();

                    User user = userBookingService.loginUser(nameToLogin, passwordToLogin);
                    try {
                        if (user != null) {
                            System.out.println("Login successful");
                        } else {
                            System.out.println("Login unsuccessful");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    // fetch bookings for a particular userId
                case 4:
                    System.out.println("Enter the source.");
                    String source = scanner.next();
                    System.out.println("Enter the destination.");
                    String destination = scanner.next();

                    List<Train> trains = userBookingService.getTrains(source.toLowerCase(), destination.toLowerCase());
                    if (trains.isEmpty()) {
                        System.out.println("No trains found");
                    } else {
                        System.out.println("Trains are " + trains);
                    }

                    break;
                case 5:
                    System.out.println("Please Login before Booking");
                    System.out.println("Enter the username for login");
                    String name = scanner.next();
                    System.out.println("Enter the password for login");
                    String password = scanner.next();


                    User loggedInUser = null;
                    try {
                        loggedInUser = userBookingService.loginUser(name, password);
                        if (loggedInUser != null) {
                            System.out.println("Login successful");
                        } else {
                            System.out.println("Login unsuccessful");
                            break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    System.out.println("Select a train");
                    System.out.println("Enter the source.");
                    String src = scanner.next();
                    System.out.println("Enter the destination.");
                    String dest = scanner.next();

                    List<Train> trainsAvailable = userBookingService.getTrains(src.toLowerCase(), dest.toLowerCase());

                    if (trainsAvailable.isEmpty()) {
                        System.out.println("No trains found");
                    } else {
                        System.out.println("Trains are " + trainsAvailable);
                    }
                    int index = 0;
                    for (Train t : trainsAvailable) {
                        System.out.println("Train Id " + t.getTrainId());
                        System.out.println("Timings of the train are :");
                        for (Map.Entry<String, LocalTime> timeEntry : t.getStationTime().entrySet()) {
                            if (timeEntry.getKey().equals(src)) {
                                System.out.println("Train " + index + " will reach source at " + timeEntry.getValue());
                            } else if (timeEntry.getKey().equals(dest)) {
                                System.out.println("Train " + index + " will reach destination at " + timeEntry.getValue());
                            }
                        }
                        index++;
                    }
                    System.out.println("Enter the desired trainId");
                    int trainBookingId = scanner.nextInt();
                    scanner.nextLine();
                    Train trainSelected = trainsAvailable.get(trainBookingId);

                    assert loggedInUser != null;
                    userBookingService.bookTicket(loggedInUser, src, dest, trainSelected);
                    System.out.println("Your ticket is booked");
                    loggedInUser.printTickets();


            }

        }while(option != 7);

    }
}
