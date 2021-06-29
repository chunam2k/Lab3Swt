/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package User;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Admin
 */
public class UserControllerTest {
    @Test
    public void testLogout() {
        new UserController().logout();
        User u = new UserController().user;
        assertEquals(null, u);
    }
}
