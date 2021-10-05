import * as React from 'react';
import { useHistory } from "react-router-dom";
import Button from '@mui/material/Button';

const Main = () => {

    const history = useHistory();

    const handleClick = (num) => {
        console.log(num);
        history.push({
          pathname: "/chat",
          props: { num:num }
      });
    };

    return (
        <div className='main'>
            <h1 style={{ fontSize: '8em' }}>Ginie Pizza</h1>
            <Button color="success" size="large" variant="contained" onClick={()=>handleClick(1)}>1호점으로 가기</Button>
            <Button color="success" size="large" variant="contained" onClick={()=>handleClick(2)}>2호점으로 가기</Button>
        </div>
    );
};

export default Main;