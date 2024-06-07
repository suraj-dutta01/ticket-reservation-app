import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import "../Styles/editbus.css"

const EditBus = () => {
    let[name,setName]=useState("")
    let[bus_number,setBus_number]=useState("");
    let[number_of_seats,setNumber_of_seats]=useState("");
    let[from_location,setFrom_location]=useState("");
    let[to_location,setTo_location]=useState("");
    let[date_of_departure,setDate_of_departure]=useState("");
    let[cost_per_seats,setCost_per_seats]=useState("")
    let[description,setDescription]=useState("")
    let[imageUrl,setImageUrl]=useState("");
    let data={name,bus_number,number_of_seats,from_location,to_location,date_of_departure,cost_per_seats,description,imageUrl};
    
    let param=useParams()
    let navigate=useNavigate();
    useEffect(()=>{
        axios.get(`http://localhost:8080/api/busses/${param.id}`)
        .then((res)=>{
            setName(res.data.data.name)
            setBus_number(res.data.data.bus_number)
            setNumber_of_seats(res.data.data.number_of_seats)
            setFrom_location(res.data.data.from_location)
            setTo_location(res.data.data.to_location)
            setDate_of_departure(res.data.data.date_of_departure)
            setCost_per_seats(res.data.data.cost_per_seats)
            setDescription(res.data.data.description)
            setImageUrl(res.data.data.imageUrl)
        })
        .catch((err)=>{
            alert("Unable To update")
            console.log(err);
        })
         
    },[])
    function editBus(e) {
        axios.put(`http://localhost:8080/api/busses/${param.id}`,data)
        .then((res)=>{
          alert("Bus Detailse Updated")
        })
        .catch((err)=>{
            alert("Invalid Data Format")
        })
    }
    return ( 
        <div className="editbus">
            <form action="">
                <label htmlFor="">
                    Name
                </label>
                <input type="text" required value={name} onChange={(e)=>{setName(e.target.value)}} placeholder="enter name" />
                <label htmlFor="">
                    Bus Number
                </label>
                <input type="text" required value={bus_number} onChange={(e)=>{setBus_number(e.target.value)}} placeholder="enter bus number" />
                <label htmlFor="">
                    Number Of Seats
                </label>
                <input type="text" required value={number_of_seats} onChange={(e)=>{setNumber_of_seats(e.target.value)}} placeholder="enter number of seats" />
                <label htmlFor="">
                    From Location
                </label>
                <input type="text" required value={from_location} onChange={(e)=>{setFrom_location(e.target.value)}} placeholder="enter from location" />
                <label htmlFor="">
                    To Location
                </label>
                <input type="text" value={to_location} required onChange={(e)=>{setTo_location(e.target.value)}} placeholder="enter to location" />
                <label htmlFor="">
                    Date Of Departure
                </label>
                <input type="date" value={date_of_departure} required onChange={(e)=>{setDate_of_departure(e.target.value)}} placeholder="enter date of departure" />
                <label htmlFor="">
                    Cost Per Seat
                </label>
                <input type="text" value={cost_per_seats} required onChange={(e)=>{setCost_per_seats(e.target.value)}} placeholder="enter cost per seat" />
                <label htmlFor="">
                    Description
                </label>
                <input type="text" value={description} required onChange={(e)=>{setDescription(e.target.value)}} placeholder="enter cost per seat" />
                <label htmlFor="">
                    Bus Image
                </label>
                <input type="text" value={imageUrl} required onChange={(e)=>{setImageUrl(e.target.value)}} placeholder="enter cost per seat" />
                
                <button onClick={editBus} id="editbusbutton">Edit</button>
            </form>
        </div>
     );
}
 
export default EditBus;