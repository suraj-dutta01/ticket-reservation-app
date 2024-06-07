import { useState } from "react";
import "../Styles/admindashbord.css"
import axios from "axios";
const AdminDashBord = () => {
    let[from_location,setFrom_location]=useState("")
    let[to_location,setTo_location]=useState("")
    let[date_of_departure,setDate_of_departure]=useState("")
    let[buses,setBuses]=useState([])
    
    function searchBus(e) {
        e.preventDefault()
        axios.get(`http://localhost:8080/api/busses/${from_location}/${to_location}/${date_of_departure}`)
        .then((res)=>{
            setBuses(res.data.data)
            console.log(res.data.data);
        })
        .catch((err)=>{
            alert("No Bus Found With That Data")
        })
    }
    return ( 
        <div className="admindashbord">
            <form action="">
                <input type="text" value={from_location} onChange={(e)=>{setFrom_location(e.target.value)}} placeholder="Enter From Location"/>
                <input type="text" value={to_location} onChange={(e)=>{setTo_location(e.target.value)}} placeholder="Enter to location"/>
                <input type="text" value={date_of_departure} onChange={(e)=>{setDate_of_departure(e.target.value)}} placeholder="Enter the Departur Date"/>
                <button onClick={searchBus}>Search</button>
            </form>
            {buses.map((item)=>{
                return(
                <div className="dashbusdetails">
                <div className="dashfirstbox">
                <h4>{item.name}</h4>
                <div className="dashseatsbox">
                <p>Date : {item.date_of_departure} |</p>
                <p>Price : {item.cost_per_seats} ₹</p>
                </div>
                <div className="dashseatsbox">
                <p>Seats : {item.number_of_seats} |</p>
                <p> Left : {item.number_of_avilable_seats}</p>
                </div>
                </div>
                <div className="dashsecondbox">
                <h5>Bus : {item.bus_number}</h5>
                <div className="dashbusrute">
                <p>From : {item.from_location}</p>
                <p>To : {item.to_location}</p>
                </div>
                <div className="dashbusbuttons">
                     <button >Book Bus</button>
                </div>
                </div>
            </div>
                )
            })}
            </div>
     );
}
 
export default AdminDashBord;