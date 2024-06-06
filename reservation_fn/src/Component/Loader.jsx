import React from 'react';
import { TailSpin } from 'react-loader-spinner';
import "../Styles/loader.css"
const Loader = ({ visible }) => {
    return ( 
        <div className={`loader-overlay ${visible ? 'visible' : 'hidden'}`}>
      {visible && (
        <TailSpin
          visible={true}
          height="80"
          width="80"
          color="#4fa94d"
          ariaLabel="tail-spin-loading"
          radius="1"
          wrapperStyle={{}}
          wrapperClass=""
        />
      )}
    </div>
     );
}
 
export default Loader;