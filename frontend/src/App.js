import { Route, Routes } from "react-router-dom";

import Home from './components/Home';
import NoMatch from './components/NoMatch';
import Search from './components/Search';
import SeedDb from './components/SeedDb';
import Sort from './components/Sort';

export default function App() {
  return (
    <div>
      <h1>New Relic Coding Challenge</h1>

      <SeedDb />

      <br />
      <hr />

      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/scenarioa" element={<Search />} />
        <Route path="/scenarioc" element={<Sort />} />
        <Route path="*" element={<NoMatch />} />
      </Routes>
    </div>
  );
}
