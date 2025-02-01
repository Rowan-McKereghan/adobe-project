import './App.css';
import fetchRoman from './fetchRoman'
import {Button, defaultTheme, Provider, Flex, Heading, TextField, Text, Switch} from '@adobe/react-spectrum';
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
          <Switch alignSelf='end' data-testid="switch" onChange={switchColorScheme}>{switchText}</Switch>
          <Heading level="2" marginTop="5vh">Integer to Roman Numeral Converter</Heading>
          <Flex direction='column' height='80vh' width="50vw" marginStart='25vw' marginEnd='35vw' marginBottom='10vh' gap="size-400" alignItems="center" justifyContent="left">
            <TextField style={{"text-align": "center"}} label="Enter your number here:" data-testid="input" value={inputInt} onChange={setInputInt}></TextField>
            <Button variant="secondary" height="size-500" data-testid="fetch-button" onPress={() => handleSubmit()}>
                    Convert To Roman Numeral
            </Button>
            <Flex direction="row" justifyContent="flex-start" width="30vw" marginStart="15vw" wrap>
              <Heading level="4">Roman Numeral:&nbsp;</Heading>
              <Text data-testid="output" alignSelf="center">{romanNumeral}</Text>
            </Flex>
          </Flex>
        </Flex>
      </Provider>
    )
  );
}

export default App;
