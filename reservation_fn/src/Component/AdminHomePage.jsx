import { Route, Routes } from "react-router-dom";
import AdminNavBar from "./AdminNavBar";
import AdminDashBord from "./AdminDashBord";
import AddBus from "./AddBus";

const AdminHomePage = () => {
    return ( 
        <div className="">
           <AdminNavBar/> 
        <Routes>
          <Route path="/" element={<AdminDashBord/>}/>
          <Route path="/addbus"element={<AddBus/>}/>
        </Routes>
        </div>
     );
}
 
export default AdminHomePage;