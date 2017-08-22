# Text To Email!

Text to Email (TtoE) is an app that farword the Received SMS to to email, there is a Setting Activity where You can enter Sender & Receiver Email.
And add spcific number in the WhiteList Activity for example if you added this number to your Whitelist +93123213123 where ever you Received SMS from this number is will automatically forward to EMAIL. 
 
## Getting started

Create an object of class GMailSender :

 ```java
1. GMailSender sender;

 ```

 You need to pass some information to ``` senser ``` object.

 ```java
2. sender = new GMailSender( Sender_Email_Address, Sender_Email_Password );

 ```
 
 Pass Subject Content of EMAIL and Receiver Email Address.
 
 ```java
3.  sender.sendMail("EmailSubject", "Email Content", Sender_Email_Address, Receiver_Email_Address);

 ```


## Installed dependencies and JAR file.

Jar Files is Added to C:/Sms006/libs/:

```json
$ component installed

    compile files('C:/Sms006/libs/activation.jar')
    compile files('C:/Sms006/libs/additionnal.jar')
    compile files('C:/Sms006/libs/mail.jar')
   
```


## Notes

no need of Note feel free to use the code.
