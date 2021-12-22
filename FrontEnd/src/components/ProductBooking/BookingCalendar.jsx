import React, { useRef, useContext } from "react";
import { Calendar, DateObject } from "react-multi-date-picker";
import datesContext from "../../context/datesContext";
import useMediaQuery from "../../hooks/useMediaQuery";
import "./BookingCalendar.css";

function BookingCalendar({ dates }) {
  const datePickerRef = useRef();
  const weekDays = ["S", "M", "T", "W", "T", "F", "S"];

  const { stateDates, setDates } = useContext(datesContext);

  const getDaysArray = function (start, end) {
    for (var arr = [], dt = start; dt <= end; dt.setDate(dt.getDate() + 1)) {
      arr.push(new Date(dt));
    }
    return arr;
  };

  let arrayDate = [];

  dates.map((dateInterval) => {
    let checkIn = new Date(dateInterval.checkIn);
    let startDate = checkIn.setDate(checkIn.getDate() + 1);
    let checkOut = dateInterval.checkOut;

    let daylist = getDaysArray(new Date(startDate), new Date(checkOut));
    return daylist.map(
      (v) => (arrayDate = [...arrayDate, new DateObject(v).format()])
    );
  });

  return (
    <div className="ContainerCalendarBooking">
      <h2>Selecciona tu fecha de reserva</h2>
      <Calendar
        numberOfMonths={useMediaQuery("(max-width: 706px)") ? 1 : 2}
        disableMonthPicker
        disableYearPicker
        minDate={new DateObject()}
        hideYear
        weekDays={weekDays}
        ref={datePickerRef}
        range
        value={stateDates}
        onChange={setDates}
        mapDays={({ date }) => {
          if (
            arrayDate.includes(date.format())
          )
            return {
              disabled: true,
              style: {
                textDecoration: "line-through",
                color: "#8798ad",
              },
            };
        }}
      />
    </div>
  );
}

export { BookingCalendar };
