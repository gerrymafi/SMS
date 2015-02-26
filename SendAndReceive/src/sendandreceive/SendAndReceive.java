/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sendandreceive;

import smsutil.TxtService;

/**
 *
 * @author madziwal
 */
public class SendAndReceive {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        TxtService txtService = new TxtService();
        TxtService.sendMessage("263732375306", "Sample Message");
    }
}
