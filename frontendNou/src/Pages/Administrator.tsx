import * as React from 'react';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import FormControlLabel from '@mui/material/FormControlLabel';
import Checkbox from '@mui/material/Checkbox';
import Link from '@mui/material/Link';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import image from './manager.png'
import background from './fundal.jpg'
import {FormControl, FormLabel, IconButton, InputAdornment, InputLabel, OutlinedInput, Radio, RadioGroup, styled} from "@mui/material";
import {useLocation, useNavigate} from "react-router-dom";
import axios from "axios";
import { DataGrid, GridColDef, GridValueGetterParams } from '@mui/x-data-grid';
import {useEffect, useState} from "react";
import VisibilityOff from "@mui/icons-material/VisibilityOff";
import Visibility from "@mui/icons-material/Visibility";
import { useTranslation } from 'react-i18next';

interface Magazin {
    magazinId: number | null;
  }
  
  interface Utilizator {
    id: number;
    userType: string;
    nume: string;
    parola: string;
    email: string;
    magazin: Magazin | null; // Allow null for magazin
  }
  
  const columns: GridColDef[] = [
    { field: 'nume', headerName: 'Nume', width: 150 },
    { field: 'userType', headerName: 'User Type', width: 150 },
    { field: 'parola', headerName: 'Parola', width: 150 },
    { field: 'email', headerName: 'Email', width: 150 },
    { field: 'magazinId', headerName: 'Magazin ID', width: 150 },
  ];
  
  const StyledFormControl = styled(FormControl)(({ theme }) => ({
    margin: theme.spacing(3),
  }));
  
  export const Administrator = (): JSX.Element => {
    const [utilizatori, setUtilizatori] = useState<Utilizator[]>([]);
    const [selectedUtilizator, setSelectedUtilizator] = useState<Utilizator | null>(null);
    const [nume, setNume] = useState<string>("");
    const [userType, setUserType] = useState<string>("Administrator");
    const [parola, setParola] = useState<string>("");
    const [email, setEmail] = useState<string>("");
    const [magazinId, setMagazinId] = useState<string>("");
  
  
    const location = useLocation();
    const id = parseInt(location.state?.key, 10);
    
    const [t, i18n] = useTranslation("global");
  
  
    const handleChangeLanguage = (lang: string) => {
        i18n.changeLanguage(lang);
    };
  
  
    useEffect(() => {
      axios.get(`http://localhost:8060/User/FindAll`, {
      }).then(response => {
        setUtilizatori(response.data);
      }).catch(error => {
        console.error(error.response.data);
        alert(`Error: ${error.response.data}`);
      });
    }, []);
  
    const handleSelectionChange = (selectionModel: any): void => {
      if (selectionModel.length > 0) {
        const selectedUtilizatorId = selectionModel[0];
        const utilizator = utilizatori.find((u) => u.id === selectedUtilizatorId) || null;
        setSelectedUtilizator(utilizator);
      } else {
        setSelectedUtilizator(null);
      }
    };
  
    const onChangeNume = (event: React.ChangeEvent<HTMLInputElement>): void => {
      setNume(event.target.value);
    };
  
    const onChangeParola = (event: React.ChangeEvent<HTMLInputElement>): void => {
      setParola(event.target.value);
    };
  
    const onChangeEmail = (event: React.ChangeEvent<HTMLInputElement>): void => {
      setEmail(event.target.value);
    };
  
    const onChangeUserType = (event: React.ChangeEvent<HTMLInputElement>): void => {
      setUserType(event.target.value);
    };
  
    const onInsert = (): void => {
      const newUtilizator: Utilizator = {
        id: 0,
        userType,
        nume,
        parola,
        email,
        magazin: magazinId ? { magazinId: parseInt(magazinId) } : null
      };
    
      if (magazinId === null) {
        // If magazinId is null, explicitly set magazin property to null
        newUtilizator.magazin = null;
      }
    
      axios.post('http://localhost:8060/User/Insert', newUtilizator, { headers: { 'Content-Type': 'application/json' } })
        .then(response => {
          setUtilizatori([...utilizatori, newUtilizator]);
        })
        .catch(error => {
          console.error(error.response.data);
          alert(`Error: ${error.response.data}`);
        });
    };
    
    
  
    const onUpdate = (): void => {
      if (!selectedUtilizator) return;
  
      const updatedUtilizator: Utilizator = {
        ...selectedUtilizator,
        userType: userType || selectedUtilizator.userType,
        nume: nume || selectedUtilizator.nume,
        parola: parola || selectedUtilizator.parola,
        email: email || selectedUtilizator.email,
      };
  
      axios.post('http://localhost:8060/User/Insert', updatedUtilizator, { headers: { 'Content-Type': 'application/json' } })
        .then(response => {
          setUtilizatori(utilizatori.map(u => u.id === selectedUtilizator.id ? updatedUtilizator : u));
        })
        .catch(error => {
          console.error(error.response.data);
          alert(`Error: ${error.response.data}`);
        });
    };
  
    const onDelete = (): void => {
      if (!selectedUtilizator) return;
  
      axios.post('http://localhost:8060/User/Delete', selectedUtilizator, { headers: { 'Content-Type': 'application/json' } })
        .then(response => {
          setUtilizatori(utilizatori.filter(u => u.id !== selectedUtilizator.id));
          setSelectedUtilizator(null);
        })
        .catch(error => {
          console.error(error.response.data);
          alert(`Error: ${error.response.data}`);
        });
    };
  
    const onSearch = (): void => {
      axios.post('http://localhost:8060/User/Search', { userType: userType }, { headers: { 'Content-Type': 'application/json' } })
        .then(response => {
          setUtilizatori(response.data);
        })
        .catch(error => {
          console.error(error.response.data);
          alert(`Error: ${error.response.data}`);
        });
    };
  
    const rows = utilizatori.map((u) => ({
      id: u.id,
      nume: u.nume,
      userType: u.userType,
      parola: u.parola,
      email: u.email,
      magazinId: u.magazin?.magazinId ?? "N/A", // Handling null magazinId
    }));
  
    return (
      <div>
        <DataGrid
          rows={rows}
          columns={columns}
          onRowSelectionModelChange={handleSelectionChange}
          checkboxSelection
        />
        <Box display="flex" justifyContent="space-between" mb={2}>
          <TextField label={t('Nume')} variant="outlined" onChange={onChangeNume} />
          <TextField label={t('Email')} variant="outlined" onChange={onChangeEmail} />
          <TextField label={t('Parola')} variant="outlined" onChange={onChangeParola} />
          <TextField label={t('MagazinId')} variant="outlined" onChange={(event) => setMagazinId(event.target.value)}
  />
        </Box>
        <Box component="fieldset" mb={2}>
          <FormLabel component="legend">User Type</FormLabel>
          <RadioGroup row aria-label="userType" name="userType" value={userType} onChange={onChangeUserType}>
            <FormControlLabel value="Administrator" control={<Radio />} label="Administrator" />
            <FormControlLabel value="Angajat" control={<Radio />} label="Angajat" />
            <FormControlLabel value="Manager" control={<Radio />} label="Manager" />
          </RadioGroup>
        </Box>
        <Box display="flex" flexDirection="column" mb={2}>
          <Box display="flex" justifyContent="space-between" mb={2}>
            <Button variant="contained" onClick={onInsert}>{t('Insert')}</Button>
            <Button variant="contained" onClick={onDelete}>{t('Delete')}</Button>
            <Button variant="contained" onClick={onUpdate}>{t('Update')}</Button>
            <Button variant="contained" onClick={onSearch}>{t('Search')}</Button>
            <Button variant="contained" onClick={() => handleChangeLanguage("en")}>English</Button>
            <Button variant="contained" onClick={() => handleChangeLanguage("ro")}>Română</Button>
            <Button variant="contained" onClick={() => handleChangeLanguage("fr")}>Français</Button>
            <Button variant="contained" onClick={() => handleChangeLanguage("de")}>Deutsch</Button>
          </Box>
        </Box>
      </div>
    );
  }
  
  export default Administrator;