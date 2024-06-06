import axios from "axios";
import { useEffect, useState } from "react";
import "../Styles/viewbus.css"
import EditIcon from '@mui/icons-material/Edit';
import DeleteForeverIcon from '@mui/icons-material/DeleteForever';
import { useNavigate } from "react-router-dom";
const ViewBus = () => {
    let[bus,setBus]=useState([])
    let admin=JSON.parse(localStorage.getItem("Admin"));
    let[fourse,setFourse]=useState(false)
    let navigate=useNavigate();
    useEffect(()=>{
    axios.get(`http://localhost:8080/api/busses/admin/${admin.id}`)
    .then((res)=>{
        setBus(res.data.data)
        console.log(res.data.data);
    })
    .catch((err)=>{
        console.log(err);
    })
    },[fourse])
    function deleteBus(id,bus_number) {
        axios.delete(`http://localhost:8080/api/busses/delete/${id}`)
        .then((res)=>{
            alert(`Bus Number ${bus_number} has been Removed from the list`)
            setFourse(!fourse)
        })
        .catch((err)=>{
            console.log(err);
            alert(`Unable To Remove Bus Numver ${bus_number}`)
        })
    }
    function navigateToEditBus(id) {
        navigate(`editbus/${id}`)
    }
    return ( 
        <div className="viewbus">
          {bus.map((item)=>{
            return(
                <div className="busdetails">
                    <div className="firstbox">
                    <h4>{item.name}</h4>
                    <p>Date : {item.date_of_departure}</p>
                    <p>Seats : {item.number_of_seats}</p>
                    </div>
                    <div className="secondbox">
                    <h5>Bus : {item.bus_number}</h5>
                    <div className="busrute">
                    <p>From : {item.from_location}</p>
                    <p>To : {item.to_location}</p>
                    </div>
                    <div className="busbuttons">
                         <EditIcon onClick={()=>{navigateToEditBus(item.id)}}  id="editbus" />
                         <DeleteForeverIcon onClick={()=>{deleteBus(item.id,item.bus_number)}} id="deletebus"/>
                    </div>
                    </div>
                </div>
            )
          })}

        </div>
     );
}
 
export default ViewBus;