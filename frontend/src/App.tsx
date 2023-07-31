import axios, { AxiosError } from 'axios';
import './App.css';

function App() {
  async function handleLogin(){
    try{
      const response = await axios('http://localhost:8080/auth/login', {
        method: 'POST',
        data: {username: "gustas", password: "123"},
      headers: {
      'Content-Type': 'application/json',
    },
    withCredentials: true,
  })
  console.log(response.data);
}catch(error){
  console.log("no catch");
  
  console.log(error);
  
}

    // const response = await fetch('http://localhost:8080/auth/login', {
    //   method: 'POST',
    //   headers: {
    //     'Content-Type': 'application/json'
    //   },
    //   body: JSON.stringify({ username: "gustas", password: "123" }),
    //   credentials: 'include'
    // })
    
    // console.log(await response.text());
    
    
  }

  async function handleRetrieveData(){
    try{
      const response = await axios('http://localhost:8080/products', {
        method: 'GET',
        withCredentials: true,
      })
     
      console.log(response.data);
    }catch(error){
      if(handleError(error as AxiosError) == 403)
        console.log("Usuário inválido ou não autorizado");
    }


    // try{
    //   const response = await fetch('http://localhost:8080/products', {method: 'GET', credentials: 'include'})
    //   console.log(response.status);

    //   if(response.status == 403)
    //     throw new Error("Usuário inválido ou não autorizado")
    // }catch(e){
    //   console.log(e);
    // }
  
  }

  function handleError(err: AxiosError): number{
    return err.response?.status as number
  }




  return (
    <>
      <button onClick={handleLogin}>Fazer login</button><hr/>
      <button onClick={handleRetrieveData}>Obter produtos</button>
    </>
  )
}

export default App
