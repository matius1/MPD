package mpd.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mpd.Algorithm.Johnson;
import mpd.Algorithm.McNaughton;
import mpd.Util.JohnsonTaskTime;
import mpd.Util.McNaughtonTaskTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Magic on 20.03.2017.
 */
@Controller
public class AppController {

    private final Johnson johnsonAlgorithm;

    private final McNaughton mcNaughton;

    @Autowired
    public AppController(Johnson johnsonAlgorithm, McNaughton mcNaughton) {
        this.johnsonAlgorithm = johnsonAlgorithm;
        this.mcNaughton = mcNaughton;
    }

    @RequestMapping(value = "/johnson", method = RequestMethod.GET)
    public String johnsonPage(){
        return "/johnsonView";
    }

    @RequestMapping(value = "/mcnaughton", method = RequestMethod.GET)
    public String mcNaughtonPage(){
        return "/mcnaughtonView";
    }

    @RequestMapping(value = "/johnson/getJohnsonResult", method = RequestMethod.GET)
    public
    @ResponseBody
    int[][] johnsonAlgorithm(@RequestParam("tasksTimes") String tasksTimes) throws JSONException, IOException {

        ObjectMapper mapper = new ObjectMapper();
        ArrayList<JohnsonTaskTime> johnsonTaskTimes = new ArrayList<>();
        JSONArray jArray = new JSONArray(tasksTimes);
            for (int i=0;i<jArray.length();i++){
                JohnsonTaskTime johnsonTaskTime = mapper.readValue(jArray.getString(i),JohnsonTaskTime.class);
                johnsonTaskTimes.add(johnsonTaskTime);
            }

        int i = 1;
        HashMap<String, Integer> processorOne = new HashMap<>();
        HashMap<String, Integer> processorTwo = new HashMap<>();
        for (JohnsonTaskTime johnsonTaskTime: johnsonTaskTimes
             ) {
            processorOne.put(("Z"+i),johnsonTaskTime.getTaskTimeMachineFirst());
            processorTwo.put(("Z"+i),johnsonTaskTime.getTaskTimeMachineSecond());
            i++;
        }

        List<String> jobs = johnsonAlgorithm.execute(processorOne,processorTwo);
        return johnsonAlgorithm.getMachineTimes(jobs,processorOne,processorTwo);
    }

    @RequestMapping(value = "/mcnaughton/getMcNaughtonResult", method = RequestMethod.GET)
    public
    @ResponseBody
    int[][] mcNaughtonAlgorithm(@RequestParam("tasksTimes") String tasksTimes,
                             @RequestParam("machineNumber") Integer machineNumber) throws JSONException, IOException {

        ObjectMapper mapper = new ObjectMapper();
        ArrayList<McNaughtonTaskTime> mcNaughtonTaskTimes = new ArrayList<>();
        JSONArray jArray = new JSONArray(tasksTimes);
        for (int i=0;i<jArray.length();i++){
            McNaughtonTaskTime mcNaughtonTaskTime = mapper.readValue(jArray.getString(i),McNaughtonTaskTime.class);
            mcNaughtonTaskTimes.add(mcNaughtonTaskTime);
        }

        int i = 1;
        HashMap<String, Integer> machine = new HashMap<>();
        for (McNaughtonTaskTime mcNaughtonTaskTime: mcNaughtonTaskTimes
                ) {
            machine.put(("Z"+i),mcNaughtonTaskTime.getTaskTime());
            i++;
        }

        return mcNaughton.execute(machine, machineNumber);
    }
}
