package smsutil;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 *
 */
public class TxtService {

    private InboundPollingThread inboundPollingThread;
    private static boolean START;
    ComputeSmsData sms = new ComputeSmsData();
    public static  SerialToGsm stg = new SerialToGsm("COM8");

    public static void setSTART(boolean START) {
        TxtService.START = START;
    }

    public void stopServer() {
        try {
            setSTART(false);
        } catch (Exception ex) {
            Logger.getLogger(TxtService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public  boolean loadConfiguration() throws Exception {
        try {
            System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<##############service started");
            inboundPollingThread = new InboundPollingThread();
            System.out.println("############ CreatedPolling Thread.");
            inboundPollingThread.start();
            return true;
        } catch (Exception ex) {
            Logger.getLogger(TxtService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public InboundPollingThread getInboundPollingThread() {
        return inboundPollingThread;
    }

   


    public static void sendMessage(String phoneNumber, String message) {
    	if(phoneNumber.charAt(0)=='0'){
    		String validPhonenumber="+263"+phoneNumber.substring(1);
    		 stg.sendSms(validPhonenumber, message);
    	}
    	else if((phoneNumber.substring(0, 4)).equals("+263")){
        stg.sendSms(phoneNumber, message);
    	}
    }
    public void readIncomingMessages() throws Exception {
     
       

        String retStr = new String("");
        String sss = new String();
        retStr = stg.checkSms();
        if (retStr.indexOf("ERROR") == -1) {
            System.out.println("###########################################new message detected");
            String phonNum = stg.readSmsSender();
            System.out.println("Phone # of sender: " + phonNum);
            //System.out.println("Recv'd SMS message: " + stg.readSms());
            String phon = "+" + phonNum;
            String message = stg.readSms();
         //   String[] tokens = message.split("\\*");
          //  String searchString = tokens[1];
            System.out.println("############################################message created");
       sss = stg.sendSms(phon, "What can we do for you today?");
            stg.delSms();
        } else {
            System.out.println("#############################no message detected");
        }

    }


    public static void main(String...strings ) throws Exception{
    	
    	//stg.sendSms("+263772339101", "Testing");
        new TxtService().loadConfiguration();
    }
    protected class InboundPollingThread extends Thread {

        @Override
        public void run() {
            setSTART(true);
            while (START || 2 < 3) {
                try {
                    Logger.getLogger(TxtService.class.getName()).log(Level.SEVERE, null, "InboundPollingThread() run.");
                   readIncomingMessages();
                    Thread.sleep((long) (0.3 * 1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Logger.getLogger(TxtService.class.getName()).log(Level.SEVERE, null, "InboundPollingThread() interrupted.");
                } catch (Exception e) {
                    e.printStackTrace();
                    Logger.getLogger(TxtService.class.getName()).log(Level.SEVERE, null, "Error in InboundPollingThread()");
                }
            }
        }
    }
}