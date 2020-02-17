using Microsoft.EntityFrameworkCore;
using Sportivo_webAPI.DAL;
using Sportivo_webAPI.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Sportivo_webAPI.Repositories
{
    public class CompanyRepository
    {
        public ICollection<Company> GetAll()
        {
            try
            {
                using (var context = new SportivoContext(new DbContextOptions<SportivoContext>()))
                {
                    var companies = context.Companies.ToList();
                    return companies;
                }
            }
            catch { return null; }
        }

        public ICollection<Company> GetCompany(int id)
        {
            try
            {
                using (var context = new SportivoContext(new DbContextOptions<SportivoContext>()))
                {
                    var company = context.Companies.Where(c => c.CompanyId == id).Include(c => c.Courts)
                        .Include(c => c.Courts)
                        .ToList();
                    return company;
                }
            }
            catch { return null; }
        }

        public int Add(Company company)
        {
            try
            {
                using (var context = new SportivoContext(new DbContextOptions<SportivoContext>()))
                {
                    context.Companies.Add(company);
                    context.SaveChanges();
                    return company.CompanyId;
                }
            }
            catch { return -1; }
        }
        public bool Update(Company company, Company updated)
        {
            try
            {
                using (var context = new SportivoContext(new DbContextOptions<SportivoContext>()))
                {
                    company.CompanyName = updated.CompanyName;
                    company.Description = updated.Description;
                    company.Latitude = updated.Latitude;
                    company.Longitude = updated.Longitude;
                    company.PhoneNumber = updated.PhoneNumber;

                    context.SaveChanges();
                    return true;
                }
            }
            catch { return false; }
        }

        public bool Delete(Company company)
        {
            try
            {
                using (var context = new SportivoContext(new DbContextOptions<SportivoContext>()))
                {
                    context.Companies.Remove(company);
                    context.SaveChanges();
                    return true;
                }
            }
            catch { return false; }
        }

        public ICollection<Company> GetAllForSport(int sportId)
        {
            try
            {
                using (var context = new SportivoContext(new DbContextOptions<SportivoContext>()))
                {
                    //var courts = context.Courts.Where(c => c.Sport.Name == sportName).ToList();
                    var companies = context.Companies.Where(c => c.Courts.Any(court => court.Sport.SportId == sportId))
                        .Include(c => c.Courts)
                        .ToList();
                    //var companies = context.Companies.Where(c => c.Courts.Contains(courts));

                    return companies.ToList();
                }
            }
            catch { return null; }
        }
    }
}
