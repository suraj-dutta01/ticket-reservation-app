import { useState } from "react";
import "../Styles/addbus.css"
import axios from "axios";
const AddBus = () => {
    let[name,setName]=useState("")
    let[bus_number,setBus_number]=useState("");
    let[number_of_seats,setNumber_of_seats]=useState("");
    let[from_location,setFrom_location]=useState("");
    let[to_location,setTo_location]=useState("");
    let[date_of_departure,setDate_of_departure]=useState("");
    let data={name,bus_number,number_of_seats,from_location,to_location,date_of_departure};
    let adminId=1;
    function addbus(e) {
        e.preventDefault()
        axios.post(`http://localhost:8080/api/busses/${adminId}`,data)
        .then((res)=>{
            alert("Bus Added")
        })
        .catch((err)=>{
            console.log(err);
            alert("Failrd to add Bus");
        })
    }
    return ( 
        <div className="addbus">
            <form action="">
                <label htmlFor="">
                    Name
                </label>
                <input type="text" required value={name} onChange={(e)=>{setName(e.target.value)}} placeholder="enter name" />
                <label htmlFor="">
                    Bus Number
                </label>
                <input type="text" required value={bus_number} onChange={(e)=>{setBus_number(e.target.value)}} placeholder="enter name" />
                <label htmlFor="">
                    Number Of Seats
                </label>
                <input type="text" required value={number_of_seats} onChange={(e)=>{setNumber_of_seats(e.target.value)}} placeholder="enter name" />
                <label htmlFor="">
                    From Location
                </label>
                <input type="text" required value={from_location} onChange={(e)=>{setFrom_location(e.target.value)}} placeholder="enter name" />
                <label htmlFor="">
                    To Location
                </label>
                <input type="text" value={to_location} required onChange={(e)=>{setTo_location(e.target.value)}} placeholder="enter name" />
                <label htmlFor="">
                    Date Of Departure
                </label>
                <input type="date" value={date_of_departure} required onChange={(e)=>{setDate_of_departure(e.target.value)}} placeholder="enter name" />
                <button onClick={addbus} id="addbusbutton">Add Bus</button>
            </form>
        </div>
     );
}
 
export default AddBus;