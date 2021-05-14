package controller;

import business.BookingManager;

public class BookingController {
  private BookingManager bookingManager;
  
  public BookingController(BookingManager bookingManager) {
    this.bookingManager = bookingManager;
  }
  
}
