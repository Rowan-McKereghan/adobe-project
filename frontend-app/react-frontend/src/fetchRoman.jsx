async function fetchRoman(userInput) {
    const url = "http://localhost:8080/romannumeral?query="
    try {
        const response = await fetch(url + userInput);

        if(response.ok) {
            const romanJSON = await response.json();
            return romanJSON.output;
        }
        else
            return await response.text();
    }
    catch (error) {
        return "Server Error: " + error;
    }

}

export default fetchRoman;