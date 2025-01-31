import './App.css';
import fetchRoman from './fetchRoman'
import {Button, defaultTheme, Provider, Flex, Header, TextField, Content, Switch} from '@adobe/react-spectrum';
import React, {useState} from 'react';

function App() {

  const [inputInt, setInputInt] = useState('');
  const [romanNumeral, setRomanNumeral] = useState('');
  const [darkMode, setDarkMode] = useState('light');
  const [switchText, setSwitchText] = useState('Light Mode');


  //fetches roman numeral json from server on event (button click)
  const handleSubmit = async () => {
    if(inputInt !== "") {
      setRomanNumeral(await fetchRoman(inputInt));
    }
  };

  //switches btwn light and dark mode on event (switch click)
  const switchColorScheme = () => {
      setDarkMode((darkMode === 'light') ? 'dark' : 'light');
      setSwitchText((darkMode === 'light') ? 'Dark Mode' : 'Light Mode');
  }


  return (
    (
      <Provider theme={defaultTheme} id="prov" colorScheme={darkMode}>
        <Flex direction="column" height='100vh' width="100vw" minHeight='30vh' minWidth="20vw"> 
          <Switch alignSelf='end' onChange={switchColorScheme}>{switchText}</Switch>
          <Flex direction='column' height='80vh' width="20vw" marginStart='43vw' marginEnd='37vw' marginY='10vh' gap="size-400" alignItems="flex-start" justifyContent="left">
            <Header>Integer to Roman Numeral Converter</Header>
            <TextField label="Input" value={inputInt} onChange={setInputInt}></TextField>
            <Button variant="accent" data-testid='button' onPress={() => handleSubmit()}>
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
