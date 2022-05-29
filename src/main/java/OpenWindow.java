import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class OpenWindow extends JPanel {
    JButton button = new JButton("whatsapp web");
    private ImageIcon background;
    private JLabel succeedToEnter;
    private TextField phoneNumber;
    private JLabel enterNumber;
    private TextField textToSend;
    private JLabel enterText;
    private ChromeDriver driver;
    private JLabel vi;
    private JLabel error;


    private boolean trySend = true;





    public static void main(String[] args) {


    }
    public OpenWindow () {
        this.setBounds(0, 0, 700, 600);
        this.setFocusable(true);
        this.requestFocus();
        this.setLayout(null);
        this.setDoubleBuffered(true);
        this.setVisible(true);
        new Thread( () -> {
            button.setBounds(250, 100, 200, 70);
            add(button);
            this.background = new ImageIcon("whatsapp.jpeg");
            succeedToEnter = new JLabel("ההתחברות בוצעה בהצלחה!");
            succeedToEnter.setBounds(250,150,300,70);
            this.succeedToEnter.setFont(new Font("TimesRoman", Font.BOLD, 20));
            this.phoneNumber = new TextField();
            this.phoneNumber.setBounds(300,250,100,30);
            enterNumber = new JLabel( "הכנס מספר נמען:");
            enterNumber.setBounds(300,220,200,20);
            this.enterNumber.setFont(new Font("TimesRoman", Font.BOLD, 15));
            enterText = new JLabel("הכנס טקסט לשליחה:");
            enterText.setBounds(300,280,200,20);
            this.enterText.setFont(new Font("TimesRoman", Font.BOLD, 15));
            this.textToSend = new TextField();
            this.textToSend.setBounds(300,320,100,50);
            this.vi = new JLabel("סטטוס");
            this.vi.setBounds(300,380,70,40);
            this.vi.setFont(new Font("TimesRoman", Font.BOLD, 15));
            this.error = new JLabel("מספר הטלפון אינו תקין");
            this.error.setBounds(350,380,70,40);
            this.error.setFont(new Font("TimesRoman", Font.BOLD, 15));
            add(phoneNumber);
            add(enterNumber);
            add(enterText);
            add(textToSend);
            add(vi);
            repaint();

        }).start();


            Thread thread = new Thread( () -> {

                //JOptionPane.showMessageDialog(null,"ההתחברות בוצעה בהצלחה (:","title",JOptionPane.INFORMATION_MESSAGE);

                AtomicBoolean valid = new AtomicBoolean(false);
                button.addActionListener((event1) -> {

                    String number = phoneNumber.getText();
                    String text = textToSend.getText();
                    if (Objects.equals(number, "")) {
                        JOptionPane.showMessageDialog(null, "not have number", "title", JOptionPane.ERROR_MESSAGE);
                        valid.set(false);

                    }
                    if (!ifValid(number)) {
                        JOptionPane.showMessageDialog(null, "Invalid number", "title", JOptionPane.ERROR_MESSAGE);
                        valid.set(false);

                    }
                    if (Objects.equals(text, "")) {
                        JOptionPane.showMessageDialog(null, "No text entered", "title", JOptionPane.ERROR_MESSAGE);
                        valid.set(false);

                    }
                    if (number.charAt(0) == '0') {
                        number = "972" + number.substring(1);
                        valid.set(true);
                    }
                    if (ifValid(number)) {
                        valid.set(true);
                    }


                    if (valid.get()) {
                        String finalNumber = number;
                        System.setProperty("webdriver.chrome.driver", "C:\\Users\\USER\\Downloads\\chromedriver_win32\\chromedriver.exe");
                        ChromeOptions chromeOptions = new ChromeOptions();
                        driver = new ChromeDriver(chromeOptions);
                        driver.manage().window().maximize();
                        driver.get("https://web.whatsapp.com/send?phone=" + finalNumber);
                        driver.manage().window().maximize();
                        var ref = new Object() {
                            boolean ifHave = false;
                        };
                      //  Thread errorT = new Thread(() ->{
                        //    while (!ref.ifHave)
                          //  try {
                            //    WebElement error = driver.findElement(By.className("_2Nr6U"));
                              //  if (error.isDisplayed()){
                                //    System.out.println("error");
                                  //  ref.ifHave =true;
                       //             WebElement clickError = driver.findElement(By.cssSelector("div[role='button'][tabindex='0']"));
                         //           clickError.click();
                                    //add(this.error);
                           //         repaint();

                             //   }

                         //   } catch (Exception e){

//                            }

  //                      });errorT.start();


                        while (trySend) {
                            try {
                                WebElement userName = driver.findElement(By.cssSelector("div[title=\"הקלדת ההודעה\"]"));
                                userName.sendKeys(text);
                                trySend = false;
                            } catch (Exception ignored) {
                            }
                        }
                        while (!trySend) {
                            try {
                                WebElement send = driver.findElement(By.xpath("//*[@id=\"main\"]/footer/div[1]/div/span[2]/div/div[2]/div[2]/button"));
                                send.click();
                                //  JOptionPane.showMessageDialog(null,"ההודעה נשלחה בהצלחה (:","title",JOptionPane.INFORMATION_MESSAGE);
                                trySend = true;
                            } catch (Exception ignored) {

                            }
                        }
                        WebElement chat = this.driver.findElement(By.className("_33LGR"));
                        WebElement chatBody = chat.findElement(By.cssSelector("div[tabindex='-1'][class='_3K4-L']"));
                        List<WebElement> allMessage = chatBody.findElements(By.className("_22Msk"));
                        WebElement lastMessage = allMessage.get(allMessage.size() - 1);
                        AtomicBoolean flag = new AtomicBoolean(true);

                        new Thread(() -> {
                            WebElement status;
                            String statusMessage= " ";
                            boolean ifSend = false;
                            while (flag.get()) {
                                try {
                                    if (!ifSend){
                                         status = lastMessage.findElement(By.cssSelector("span[data-testid='msg-check']"));
                                         statusMessage = status.getAttribute("aria-label");
                                        if (statusMessage.equals(" נשלחה ")) {
                                            System.out.println("נשלחה");
                                            vi.setText("V");
                                            repaint();
                                            ifSend = true;
                                        }
                                    }
                                    status = lastMessage.findElement(By.cssSelector("span[data-testid='msg-dblcheck']"));
                                    statusMessage = status.getAttribute("aria-label");

                                    //Thread.sleep(50000);

                                    if (statusMessage.equals(" נמסרה ")) {
                                        System.out.println("נמסרה");
                                        add(vi);
                                        vi.setText("VV");
                                        repaint();
                                    }
                                    if (statusMessage.equals(" נקראה ")) {
                                        System.out.println("נקראה");
                                        vi.setText("VV");
                                        vi.setForeground(Color.blue);
                                        repaint();
                                        flag.set(false);
                                    }

                                } catch (Exception e) {
                                    System.out.println(".");

                                }
                            }
                            try {
                                while (true){
                                    List <WebElement> comment = driver.findElements(By.cssSelector("div[tabindex = '-1'][role = 'region']"));
                                    for (int i=0; i< comment.size(); i++){
                                         List<String > mes = Collections.singletonList(comment.get(i).getText());
                                      List<String>m = List.of(mes.get(0).split(" שלא "));
                                      if (m.size() > 1) {
                                          System.out.println(m.get(1) + "ההודעה האחרונה");
                                      }
                                        for (int j=0; j<m.size(); j++) {
                                            System.out.println(m.get(j)+ " " + j + " " + " good");
                                        }
                                   //     System.out.println(mes);
                                     //   System.out.println(comment.get(i).getText());
                                        String last = comment.get(comment.size()-1).getAttribute("aria-label");
                                      //  System.out.println(last);
                                        Thread.sleep(10000);
                                    }


                                }


                            }catch (Exception e){

                            }

                        }).start();






                    }
                });

            });thread.start();


    }


    public void sendMessage(ChromeDriver driver, String message){
        while (trySend){
            try{
                WebElement userName =driver.findElement(By.cssSelector("div[title=\"הקלדת ההודעה\"]"));
                userName.sendKeys(message);
                trySend = false;
            }catch (Exception e){
                sendMessage(driver, message);
            }
        }
    }



    public static boolean ifValid (String phoneNumber) {
        boolean isValid =false;
        if (phoneNumber.charAt(0)=='9' && phoneNumber.charAt(1)=='7' && phoneNumber.charAt(2)=='2' && phoneNumber.length()==12) {
            isValid=true;
        }
        if (phoneNumber.length()==10 && phoneNumber.charAt(0) =='0' && phoneNumber.charAt(1)=='5'){
            isValid= true;
        }
      return isValid;
    }

            protected void paintComponent (Graphics g){
                super.paintComponent(g);
                if (this.background != null) {
                    g.drawImage(this.background.getImage(), 0, 0, 700, 600, null);
                }
            }
        }


