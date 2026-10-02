package ticket;

import java.util.Scanner;
import java.time.DayOfWeek;
import java.time.LocalDate;
public class movie {

	public movie() {
		// TODO Auto-generated constructor stub
	}

		    static boolean[][] seats = new boolean[5][5];
		    static Scanner sc = new Scanner(System.in);

		    // Display seats
		    static void showSeats() {
		        System.out.println("\n--- Theatre Seats ---");

		        for (int i = 0; i < 5; i++) {
		            for (int j = 0; j < 5; j++) {
		                System.out.print(seats[i][j] ? "[B] " : "[A] ");
		            }
		            System.out.println();
		        }
		    }

		    // Calculate ticket price
		    static double getTicketPrice() {
		        DayOfWeek day = LocalDate.now().getDayOfWeek();

		        if (day == DayOfWeek.SATURDAY ||
		            day == DayOfWeek.SUNDAY) {
		            return 200;
		        }

		        return 150;
		    }

		    // Book ticket
		    static void bookTicket() {
		        showSeats();

		        System.out.print("Enter row (1-5): ");
		        int row = sc.nextInt();

		        System.out.print("Enter seat (1-5): ");
		        int col = sc.nextInt();

		        if (row < 1 || row > 5 || col < 1 || col > 5) {
		            System.out.println("Invalid seat number.");
		            return;
		        }

		        if (seats[row - 1][col - 1]) {
		            System.out.println("Seat already booked.");
		            return;
		        }

		        double price = getTicketPrice();

		        System.out.println("Ticket price: Rs. " + price);
		        System.out.print("Enter coupon (SAVE10 or NONE): ");
		        String coupon = sc.next();

		        if (coupon.equalsIgnoreCase("SAVE10")) {
		            price = price * 0.90;
		            System.out.println("10% discount applied.");
		        } else if (!coupon.equalsIgnoreCase("NONE")) {
		            System.out.println("Invalid coupon. No discount.");
		        }

		        seats[row - 1][col - 1] = true;

		        System.out.println("Booking successful!");
		        System.out.println("Seat: " + row + "-" + col);
		        System.out.println("Final price: Rs. " + price);
		    }

		    // Cancel ticket
		    static void cancelTicket() {
		        System.out.print("Enter row (1-5): ");
		        int row = sc.nextInt();

		        System.out.print("Enter seat (1-5): ");
		        int col = sc.nextInt();

		        if (row < 1 || row > 5 || col < 1 || col > 5) {
		            System.out.println("Invalid seat number.");
		            return;
		        }

		        if (seats[row - 1][col - 1]) {
		            seats[row - 1][col - 1] = false;
		            System.out.println("Ticket cancelled successfully.");
		        } else {
		            System.out.println("Seat is not booked.");
		        }
		    }

		    // Movie rating
		    static void rateMovie() {
		        System.out.print("Enter rating (1-5): ");
		        int rating = sc.nextInt();

		        if (rating < 1 || rating > 5) {
		            System.out.println("Invalid rating.");
		            return;
		        }

		        System.out.println("Thank you for rating the movie!");
		    }

		    // Main method
		    public static void main(String[] args) {

		        int choice;

		        do {
		            System.out.println("\n===== MOVIE TICKET BOOKING =====");
		            System.out.println("1. Show Available Seats");
		            System.out.println("2. Book Ticket");
		            System.out.println("3. Cancel Ticket");
		            System.out.println("4. Movie Rating");
		            System.out.println("5. Exit");

		            System.out.print("Enter your choice: ");
		            choice = sc.nextInt();

		            switch (choice) {
		                case 1:
		                    showSeats();
		                    break;

		                case 2:
		                    bookTicket();
		                    break;

		                case 3:
		                    cancelTicket();
		                    break;

		                case 4:
		                    rateMovie();
		                    break;

		                case 5:
		                    System.out.println("Thank you!");
		                    break;

		                default:
		                    System.out.println("Invalid choice.");
		            }

		        } while (choice != 5);

		        sc.close();
		    }
		
	}


