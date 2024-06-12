import { useEffect, useState } from 'react';
import axios from 'axios';
import "../Styles/bookbusseat.css"
import CloseIcon from '@mui/icons-material/Close';
const BookBus = ({id,bookingPopup}) => {
    let[bus,setBus]=useState([])
    let[userId,setUserId]=useState("");
    let[noOfSeat,setNoOfSeat]=useState("")
    let[bookStatus,setBookStatus]=useState(false)
    let[show,setShow]=useState(bookingPopup);
    useEffect(()=>{
        axios.get(`http://localhost:8080/api/busses/${id}`)
        .then((res)=>{
            setBus(res.data.data)
            console.log(res.data.data);
        })
        .catch((err)=>{
            console.log(err);
        })
    },[bookStatus])
    function bookseat(e) {
        e.preventDefault()
        axios.post(`http://localhost:8080/api/tickets/${userId}/${bus.id}/${noOfSeat}`)
        .then((res)=>{
            alert("Ticket Booked successfull")
            setBookStatus(!bookStatus)
        })
        .catch((err)=>{
            alert("Unable to book Ticket")
        })
    }
    function closebookseat(e) {
        e.preventDefault()
        setShow(false)
    }
    return ( 
        show &&
            <div className="bookbusseat">
                <CloseIcon onClick={closebookseat} id="closebookseat"/>
            <form >
                    <div className="bookseatbox1">
                    <h5>From : {bus.from_location} | </h5>
                    <h5>To : {bus.to_location} | </h5>
                    <h5>Date : {bus.date_of_departure}</h5>
                    </div>
                    <div className="bookseatbox1">
                    <h5>Avilable : {bus.number_of_avilable_seats}</h5>
                    <div className="">
                    <input type="text" value={noOfSeat} onChange={(e)=>{setNoOfSeat(e.target.value)}} placeholder='Seats ' />
                    <input type="text" value={userId} onChange={(e)=>{setUserId(e.target.value)}} placeholder='user id'/>
                    </div>
                    </div>
                    <button onClick={bookseat} >Book Now</button>
            </form>
        </div>
        
     );
}
 
export default BookBus;