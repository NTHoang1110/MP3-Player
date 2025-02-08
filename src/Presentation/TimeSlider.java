package Presentation;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;

public class TimeSlider extends HBox {
    private Label start;
    private Label end;

    private Slider slider;
    private long max;
    private int INTERVAL_MS = 10;

    private SimpleIntegerProperty time;
    private NumberFormat formatter;

    public TimeSlider(){

        slider = new Slider(0, max, INTERVAL_MS);
        time = new SimpleIntegerProperty();

        start = new Label();
        end = new Label();

        this.setHgrow(slider, Priority.ALWAYS);
        this.setMaxWidth(300);
        this.getChildren().addAll(start, slider, end);
        this.setAlignment(Pos.CENTER);

        formatter = new NumberFormat() {
            private String timeString = "%02d:%02d";

            @Override
            public StringBuffer format(double number, StringBuffer toAppendTo, FieldPosition pos) {
                return null;
            }

            @Override
            public StringBuffer format(long number, StringBuffer toAppendTo, FieldPosition pos) {
                long min, sec;

                min = number / 60;
                sec = number % 60;

                toAppendTo.append(String.format(timeString, min, sec));
                return toAppendTo;
            }

            @Override
            public Number parse(String source, ParsePosition parsePosition) {
                return 0;
            }
        };

        time.bindBidirectional(slider.valueProperty());
        start.textProperty().bindBidirectional(time, formatter);
        end.setText(String.format("%02d:%02d", max/60, max%60));
    }

    public Slider getSlider() {return slider;}
    public Label getEnd() {return end;}
}
