# Chat : Spring boot & Angular 2

### Resources:
* Spring Boot
* Angular 2
* Websocket
* Slack bot
* MongoDB database

***

### Send message:

````java
@Autowired
public ChatService(SimpMessagingTemplate template) {
    this.template = template;
}
  
public void post(String room, String name, String message) {
    this.template.convertAndSend(room,  name + ": " + message);
    slack(message);
    postToDatabase(room, name, message);
}

public void slack(Chat message){
  SlackConfig config = new SlackConfig();
  Slack slack = Slack.getInstance(config);
  String token = "TOKEN";
  MethodsClient methods = slack.methods(token);
  ChatPostMessageRequest request = ChatPostMessageRequest.builder()
    .channel("#chat")
    .username(message.getName())
    .text(message.getMessage())
    .build();
  
  try {
    ChatPostMessageResponse response = slack.methods(token).chatPostMessage(req -> req
        .channel("#chat")
        .username(message.getName())
        .text(message.getMessage()));
    } catch (IOException e) {
        e.printStackTrace();
    } catch (SlackApiException e) {
        e.printStackTrace();
    }
}

public boolean postToDatabase(String room, String name, String message){
    Chat latestId = chatRepository.findTopByOrderByIdDesc();
    if(latestId != null) {
      id = latestId.getId()+1;
    }else {
      id = 0;
    }
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    Date date = new Date();
    Chat chat = new Chat(id, name, message, formatter.format(date));
    if(chatRepository.save(chat) != null) {
      return true;
    }

    return false;
}
```` 

### Get respose from Slack
````java
@Bean
public void getMessageFromSlack(){
    String botToken = "Token";
    SlackletService slackService = new SlackletService(botToken);
    slackService.addSlacklet(new Slacklet() {
        @Override
        public void onMessagePosted(SlackletRequest req, SlackletResponse resp) {
            // get message content
            String content = req.getContent();
            if(content.startsWith("%")){
              String displayC = content.replaceFirst("%", "");
              TextMessage message = new TextMessage(displayC);
              template.convertAndSend("/chatRoom",  "Admin" + ": " + message.getPayload());
              postToDatabase("/chatRoom", "Admin", message.getPayload());
            }
        }
    });
    try {
      slackService.start();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
````



