import './App.css';
import fetchRoman from './fetchRoman'
import {Button, defaultTheme, Provider, Flex, Header, TextField, Content} from '@adobe/react-spectrum';
import React, {useState} from 'react';

function App() {

  const [inputInt, setInputInt] = useState('');
  const [romanNumeral, setRomanNumeral] = useState('');

  const handleSubmit = async () => {
    if(inputInt !== "") {
      setRomanNumeral(await fetchRoman(inputInt));
    }
  };


  return (
    (
      //TODO: add colorScheme=${} variable that changes btwn light theme and dark theme
      <Provider theme={defaultTheme} colorScheme='light'>
        <Flex direction="row" height='100vh' minHeight='30vh' width="100vw" minWidth="20vw"> 
          <Flex direction='column' height='80vh' width="20vw" marginStart='43vw' marginEnd='37vw' marginY='10vh' gap="size-400" alignItems="flex-start" justifyContent="left">
            <Header>Integer to Roman Numeral Converter</Header>
            <TextField label="Input" value={inputInt} onChange={setInputInt}></TextField>
            <Button variant="accent" onPress={() => handleSubmit()}>
                    Convert To Roman Numeral
            </Button>
            <Content>Roman Numeral: {romanNumeral}</Content>
          </Flex>
        </Flex>
      </Provider>
    )
  );
}

export default App;
