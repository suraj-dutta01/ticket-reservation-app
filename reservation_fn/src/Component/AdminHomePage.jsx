import { Route, Routes } from "react-router-dom";
import AdminNavBar from "./AdminNavBar";
import AdminDashBord from "./AdminDashBord";
import AddBus from "./AddBus";
import ViewBus from "./ViewBus";
import EditBus from "./EditBus";

const AdminHomePage = () => {
    return ( 
        <div className="">
           <AdminNavBar/> 
        <Routes>
          <Route path="/" element={<AdminDashBord/>}/>
          <Route path="/addbus" element={<AddBus/>}/>
          <Route path="/viewbus" element={<ViewBus/>}/>
          <Route path="/viewbus/editbus/:id" element={<EditBus/>}/>
        </Routes>
        </div>
     );
}
 
export default AdminHomePage;