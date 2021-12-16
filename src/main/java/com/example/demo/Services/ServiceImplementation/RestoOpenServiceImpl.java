    package com.example.demo.Services.ServiceImplementation;



    import com.example.demo.Exceptions.BadRequestException;
    import com.example.demo.Models.Hrs;
    import com.example.demo.Payload.ProvidedHrs;
    import com.example.demo.Services.RestoOpenService;
    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.springframework.http.HttpStatus;
    import org.springframework.stereotype.Service;

    import java.time.DayOfWeek;
    import java.util.ArrayList;

    @Service
    public class RestoOpenServiceImpl implements RestoOpenService {

        Logger logger = LoggerFactory.getLogger(RestoOpenServiceImpl.class);

        private int dayCounter = 1;


        /**
         * @sundayClosing Sunday closing time is temporarily stored here when it closes on monday.
         * */
        private String sundayClosing = "";


        @Override
        public String doConversion(ProvidedHrs inputTime) {

            if(!inputTime.getMonday().isEmpty() &&  inputTime.getMonday().get(0).getType().equalsIgnoreCase("close")){
                sundayClosing = convertToTime(inputTime.getMonday().get(0).getValue());
                inputTime.getMonday().remove(0);
            }

            ArrayList<ArrayList<Hrs>> days = new ArrayList<>();
            days.add(inputTime.getMonday());
            days.add(inputTime.getTuesday());
            days.add(inputTime.getWednesday());
            days.add(inputTime.getThursday());
            days.add(inputTime.getFriday());
            days.add(inputTime.getSaturday());
            days.add(inputTime.getSunday());

            StringBuilder response = new StringBuilder("RESTAURANT OPENING/CLOSING HOURS");

            for (ArrayList<Hrs> day : days) {
                timeGetter(day, response);
            }

            //Output to be printed on the console
            System.out.println("");
            System.out.println("#".repeat(30));
            System.out.println(response);
            System.out.println("#".repeat(30));
            System.out.println("");
            System.out.println("");


            /**
             Restoring counter to Monday in wait for the next operation
             * */
            dayCounter = 1;

            return response + sundayClosing;
        }



        @SuppressWarnings("DuplicateCondition")
        private void timeGetter(ArrayList<Hrs> dayTimes, StringBuilder response) {

            if(dayTimes == null || dayTimes.isEmpty()){
                response.append("\n" + DayOfWeek.of(dayCounter) + ": " + "Closed");
            }
            else {
                if (dayTimes.get(0).getType().equalsIgnoreCase("open")
                        && (response.charAt(response.length()-1)!= 'S'
                        || response.charAt(response.length()-1)!= '-'
                        || response.toString().isEmpty())){

                    response.append("\n" + DayOfWeek.of(dayCounter) + ": ");

                }
                else if (dayTimes.get(0).getType().equalsIgnoreCase("close")
                        && response.charAt(response.length()-1) == '-') {
                    response.append(convertToTime(dayTimes.get(0).getValue()));
                    dayTimes.remove(0);
                    response.append("\n" + DayOfWeek.of(dayCounter) + ": ");

                    if (dayTimes.get(0).getType().equalsIgnoreCase("close")){
                        throw new BadRequestException("Invalid Request Format: Restaurant is already closed",
                                HttpStatus.BAD_REQUEST);
                    }
                }

                for (int i = 0; i < dayTimes.size(); i++) {
                    Hrs dayTime = dayTimes.get(i);

                    if (dayTime.getType().equalsIgnoreCase("open")) {

                        response.append(convertToTime(dayTime.getValue()) + "-");
                    }

                    if (dayTime.getType().equalsIgnoreCase("close")) {

                        response.append(convertToTime(dayTime.getValue()));

                        if(i != dayTimes.size()-1){
                            response.append(", ");
                        }
                    }
                }
            }
            if (dayCounter < 7) {
                dayCounter++;
            }
        }



        public String convertToTime(Integer time) {

            if(time < 0 || time > 86399){
                throw new BadRequestException("Request must an Integer with value within the range of 0 to 86399",
                        HttpStatus.BAD_REQUEST);
            }

            int hours = time / 3600;
            int minutes = (time % 3600) / 60;
            String meridian = "am";

            if(hours == 0){
                return "12:" + String.format("%02d", minutes) + meridian;
            }

            boolean isPm = hours >= 12;
            hours = (isPm && hours > 12) ? hours % 12 : hours;
            meridian = isPm ? "pm" : meridian;

            return String.format("%02d", hours) + ":" + String.format("%02d", minutes) + meridian;
        }
    }