import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class OpenWindow extends JPanel {
    JButton button = new JButton("whatsapp web");
    private ImageIcon background;
    private JLabel succeedToEnter;
    private TextField phoneNumber;
    private JButton confirm;
    private JLabel enterNumber;
    private TextField textToSend;
    private JLabel enterText;
    private ChromeDriver driver;
    private JLabel vi1;
    private JLabel vi2;
    private JLabel blueVi;

    private boolean trySend = true;
    private boolean trySend2 = true;





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
            this.confirm = new JButton("אישור");
            this.confirm.setBounds(300,380,70,40);
            this.vi2 = new JLabel("VV");
            this.vi2.setBackground(Color.black);
            this.vi2.setBounds(300,380,70,40);
            this.vi2.setFont(new Font("TimesRoman", Font.BOLD, 15));
            this.vi1 = new JLabel("סטטוס");
            this.vi1.setBackground(Color.black);
            this.vi1.setBounds(300,380,70,40);
            this.vi1.setFont(new Font("TimesRoman", Font.BOLD, 15));
            this.blueVi = new JLabel("VV");
            this.blueVi.setBackground(Color.blue);
            this.blueVi.setForeground(Color.blue);
            this.blueVi.setBounds(300,380,70,40);
            this.blueVi.setFont(new Font("TimesRoman", Font.BOLD, 15));
            add(phoneNumber);
            add(enterNumber);
            add(enterText);
            add(textToSend);
            add(vi1);
            //add(confirm);



            repaint();

        }).start();



       // button.addActionListener(event -> {

            //     chromeOptions.addArguments("C:\\Users\\USER\\AppData\\Local\\Google\\Chrome\\User Data\\Default");
        //    driver.get("https://web.whatsapp.com");
            var ref = new Object() {
                boolean flag = false;
            };
            Thread thread = new Thread( () -> {
                // while (!ref.flag) {
                //  List<WebElement> elementList = driver.findElements(By.linkText("ניתן להוריד אותה כאן"));
                //    for (int i = 0; i < elementList.size(); i++) {
                //      if (elementList.get(i).isDisplayed()) {
                //        ref.flag =true;
                //  }
                //   }
                /// }
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
                        number = "972" + number.substring(1, number.length());
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
                        // sendMessage(driver,text);
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
                        var ref1 = new Object() {
                            boolean ifSend = false;
                        };
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
                                            vi1.setText("V");
                                            repaint();
                                            ifSend = true;
                                        }
                                    }
                                    status = lastMessage.findElement(By.cssSelector("span[data-testid='msg-dblcheck']"));
                                    statusMessage = status.getAttribute("aria-label");

                                    //Thread.sleep(50000);

                                    if (statusMessage.equals(" נמסרה ")) {
                                        System.out.println("נמסרה");
                                        vi1.setText("VV");
                                        repaint();
                                    }
                                    if (statusMessage.equals(" נקראה ")) {
                                        System.out.println("נקראה");
                                        vi1.setText("VV");
                                        vi1.setForeground(Color.blue);
                                        repaint();
                                        flag.set(false);
                                    }

                                } catch (Exception e) {
                                    System.out.println(".");

                                }
                            }
                            WebElement comment = driver.findElement(By.xpath("//*[@id=\"main\"]/div[3]/div/div[2]/div[3]/div[47]/div/div[1]/div[1]/div[1]/div/span[1]/span"));
                            System.out.println(comment.getText());
                        }).start();






                    }
                });

            });thread.start();


    }
    public Component printVi (Color color, String vi){
        JLabel label = new JLabel(vi);
        label.setBackground(color);
        this.blueVi.setBounds(300,380,70,40);
        this.blueVi.setFont(new Font("TimesRoman", Font.BOLD, 15));

        return label;
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


