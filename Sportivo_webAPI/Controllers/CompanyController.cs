using Microsoft.AspNetCore.Mvc;
using Sportivo_webAPI.Models;
using Sportivo_webAPI.Repositories;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Sportivo_webAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class CompanyController : Controller
    {
        private readonly CompanyRepository _companyRepository;
        public CompanyController()
        {
            _companyRepository = new CompanyRepository();
        }

        [HttpGet]
        [Route("getAll")]
        public IActionResult Get(int sportId)
        {
            return Ok(_companyRepository.GetAllForSport(sportId));
        }

        [HttpGet]
        [Route("getById")]
        public IActionResult GetById(int companyId)
        {
            return Ok(_companyRepository.GetCompany(companyId));
        }

        [HttpPost]
        [Route("register")]
        public IActionResult Register([FromBody]Company company)
        {
            return Ok(_companyRepository.Add(company));
        }
    }
}
