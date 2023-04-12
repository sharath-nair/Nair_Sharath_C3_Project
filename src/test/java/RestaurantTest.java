import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;

@ExtendWith(MockitoExtension.class)
class RestaurantTest {
    Restaurant restaurant;
    //REFACTOR ALL THE REPEATED LINES OF CODE
    @BeforeEach
    public void setup(){
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);


        // service=new RestaurantService();
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        Restaurant spyRestaurant = spy(restaurant);
        LocalTime currentTimeMock = LocalTime.parse("13:30:00");
        Mockito.when(spyRestaurant.getCurrentTime()).thenReturn(currentTimeMock);

        assertTrue(spyRestaurant.isRestaurantOpen());        
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        Restaurant spyRestaurant = spy(restaurant);
        LocalTime currentTimeMock = LocalTime.parse("23:30:00");
        Mockito.lenient().when(spyRestaurant.getCurrentTime()).thenReturn(currentTimeMock);

        assertFalse(spyRestaurant.isRestaurantOpen());        
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    @Test
    public void calculate_order_total_returns_total_cost_of_added_items(){
        ArrayList<String> items =  new ArrayList<String>();
        items.add("Vegetable lasagne");
        assertEquals(269,restaurant.calculateOrderTotal(items));
    }

    @Test
    public void adding_item_to_list_should_increase_total_cost(){
        ArrayList<String> items = new ArrayList<String>();
        items.add("Sweet corn soup");
        assertEquals(119,restaurant.calculateOrderTotal(items));
        items.add("Vegetable lasagne");
        assertEquals(388,restaurant.calculateOrderTotal(items));
    }

    @Test
    public void total_price_decreases_if_items_are_removed_from_list(){
        ArrayList<String> items = new ArrayList<String>();
        items.add("Sweet corn soup");
        items.add("Vegetable lasagne");
        assertEquals(388,restaurant.calculateOrderTotal(items));
        items.remove("Vegetable lasagne");
        assertEquals(119,restaurant.calculateOrderTotal(items));
    }
}