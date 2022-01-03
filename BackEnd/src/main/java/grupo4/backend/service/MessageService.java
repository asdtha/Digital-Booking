//package grupo4.backend.service;
//import lombok.Value;
//import nl.martijndwars.webpush.Notification;
//import nl.martijndwars.webpush.PushService;
//import nl.martijndwars.webpush.Subscription;
//import org.jose4j.lang.JoseException;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//import javax.annotation.PostConstruct;
//import java.io.IOException;
//import java.security.GeneralSecurityException;
//import java.security.Security;
//import java.time.LocalTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.ExecutionException;
//
//@Service
//public class MessageService {
//
//    @Value("${vapid.public.key}")
//    public String publicKey;
//    @Value("{vapid.private.key}")
//    private String privateKey;
//
//    private PushService pushService;
//
//    private List<Subscription> subscriptions = new ArrayList<>();
//
//
//    @PostConstruct
//    private void init() throws GeneralSecurityException {
//        Security.addProvider(new BouncyCastleProvider());
//        pushService = new PushService(publicKey, privateKey);
//    }
//
//    public String getPublicKey() {
//        return publicKey;
//    }
//
//    public void subscribe(Subscription subscription) {
//        System.out.println("Te has suscrito a " + subscription.endpoint);
//        this.subscriptions.add(subscription);
//    }
//
//    public void unsubscribe(String endpoint) {
//        System.out.println("Has finalizado tu suscripción a " + endpoint);
//        subscriptions = subscriptions.stream().filter(s -> !endpoint.equals(s.endpoint)).col
//    }
//
//    public void sendNotification(Subscription subscription, String messageJson) {
//        try {
//            pushService.send(new Notification(subscription, messageJson));
//        } catch (GeneralSecurityException | IOException | ExecutionException | JoseException | InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        @Scheduled(fixedRate = 30000)
//        private void sendNotifications () {
//            System.out.println("Enviando notificaciones a todos los suscriptores");
//
//            var json = """
//                    {
//                    "title": "Server says hello!",
//                    "body": "It is now %s"
//                    }
//                    """;
//
//            subscriptions.forEach(subscription1 -> {
//                sendNotification(subscription1, String.format(json, LocalTime.now()))
//            });
//        }
//    }
//}