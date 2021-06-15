package presenters;
import usecases.*;
import gateways.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ScheduleBuilder {
    EventManager em;
    FileGateway gateway;
    public ScheduleBuilder(EventManager em, FileGateway gateway) {
        this.em = em;
        this.gateway = gateway;
    }


    public void buildEventList() {
        ArrayList<HashMap<String,String>> events;
        events = em.getDictofAllEvent();
        StringBuilder content = new StringBuilder();
        for (HashMap<String,String> e : events) {
            content.append("<tr>");
            content.append("<td class=\"column1\">" +e.get("identifier")+ "</td>");
            content.append("<td class=\"column2\">" +e.get("name")+ "</td>");
            String speakers = e.get("speakerUsernames");
            speakers = speakers.replace("[","");
            speakers = speakers.replace("]","");
            content.append("<td class=\"column3\">" +speakers+ "</td>");
            String startTime = e.get("startTime");
            startTime = startTime.replace("T"," ");
            content.append("<td class=\"column4\">" + startTime+ "</td>");
            content.append("<td class=\"column5\">" +e.get("durationInMinutes")+ "</td>");
            content.append("<td class=\"column6\">" +e.get("room")+ "</td>");
            content.append("<td class=\"column6\">" + e.get("isVIP") + "</td>");
            content.append("</tr>");
        }
        gateway.saveEventHTML(content.toString());
        System.out.println("Saved schedule to local disk!");
    }

}
