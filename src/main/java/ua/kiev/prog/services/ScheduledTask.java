package ua.kiev.prog.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.*;
import ua.kiev.prog.turbosms.Message;
import ua.kiev.prog.turbosms.SMS;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

public class ScheduledTask extends TimerTask {
    private static final Gson gson = new GsonBuilder().create();
    private static final String uri = "https://api.turbosms.ua/message/send.json";
    private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    String taskName;
    String recipient;

    public ScheduledTask(String taskName, String recipient) {
        this.taskName = taskName;
        this.recipient = recipient;
    }

    @Override
    public void run() {
        SMS sms = new SMS("Prog_Notify", this.taskName);
        Message message = new Message(new String[]{this.recipient}, sms);
        String json = gson.toJson(message);

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(
                json,
                MediaType.parse("application/json; charset=utf-8")
        );
        Request request = new Request.Builder()
                .url(uri)
                .addHeader("Authorization", "Basic a03dbf63884ac97a003b4e6043027b42bc1c9646")
                .post(body)
                .build();
        Call call = client.newCall(request);

        Response response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(df.format(new Date()) + ": " + response);
        if (response != null) {
            response.close();
        }
    }
}
