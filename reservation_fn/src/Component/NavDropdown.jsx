import Dropdown from 'react-bootstrap/Dropdown';
const NavDropdown = () => {

    return (
        <Dropdown>
          <Dropdown.Toggle variant="success" id="dropdown-basic">
            Account
          </Dropdown.Toggle>
    
          <Dropdown.Menu>
            <Dropdown.Item href="/adminhomepage/addbus">AddBus</Dropdown.Item>
            <Dropdown.Item href="/adminhomepage/viewbus">Busess List</Dropdown.Item>
            <Dropdown.Item href="#/action-3">Edit Profile</Dropdown.Item>
            <Dropdown.Item href="/adminhomepage">Home</Dropdown.Item>
            <Dropdown.Item href="#/action-4">Logout</Dropdown.Item>
          </Dropdown.Menu>
        </Dropdown>
      );
}
 
export default NavDropdown;